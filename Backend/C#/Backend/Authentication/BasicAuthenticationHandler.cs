using System;
using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using System.Text.Encodings.Web;
using System.Threading.Tasks;
using Backend.Models;
using Backend.Services;
using Microsoft.AspNetCore.Authentication;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;

namespace Backend.Authentication {
    public class BasicAuthenticationHandler : AuthenticationHandler<AuthenticationSchemeOptions> {
        private readonly IUserService _userService;

        public BasicAuthenticationHandler(
            IOptionsMonitor<AuthenticationSchemeOptions> options,
            ILoggerFactory logger,
            UrlEncoder encoder,
            ISystemClock clock,
            IUserService userService)
            : base(options, logger, encoder, clock) {
            _userService = userService;
        }

#pragma warning disable 1998
        protected override async Task<AuthenticateResult> HandleAuthenticateAsync() {
#pragma warning restore 1998
            
            if (!Request.Headers.ContainsKey("Authorization")) {
                return AuthenticateResult.Fail("Missing Authorization Header");
            }

            User user;
            try {
                AuthenticationHeaderValue authHeader = AuthenticationHeaderValue.Parse(Request.Headers["Authorization"]);
                byte[] credentialBytes = Convert.FromBase64String(authHeader.Parameter);
                string[] credentials = Encoding.UTF8.GetString(credentialBytes).Split(':');
                string username = credentials[0];
                string password = credentials[1];
                user = _userService.Authenticate(username, password);
            } 
            catch (Exception e) when (!(e is AuthenticationException)) {
                return AuthenticateResult.Fail("Invalid Authorization Header");
            }

            if (user == null) {
                return AuthenticateResult.Fail("Invalid Username or Password");
            }

            Claim[] claims = { 
                new Claim("Id", user.Id.ToString()),
                new Claim("Admin", user.IsAdmin.ToString())
            };
            ClaimsIdentity identity = new ClaimsIdentity(claims, Scheme.Name);
            ClaimsPrincipal principal = new ClaimsPrincipal(identity);
            AuthenticationTicket ticket = new AuthenticationTicket(principal, Scheme.Name);

            return AuthenticateResult.Success(ticket);
        }
    }
}