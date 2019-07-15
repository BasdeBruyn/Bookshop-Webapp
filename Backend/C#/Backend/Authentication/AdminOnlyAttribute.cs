using System;
using System.Security.Claims;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Filters;

namespace Backend.Authentication {
	public class AdminOnlyAttribute : Attribute, IAuthorizationFilter {
		public void OnAuthorization(AuthorizationFilterContext context) {
			ClaimsPrincipal principal = context.HttpContext.User;
			if (!AuthService.IsAdmin(principal))
				context.Result = new UnauthorizedResult();
		}
	}
}