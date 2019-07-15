using AutoMapper;
using Backend.Authentication;
using Backend.Models;
using Backend.Services;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;

namespace Backend {
	public class Startup {
		public Startup(IConfiguration configuration) {
			Configuration = configuration;
		}

		public IConfiguration Configuration { get; }

		// This method gets called by the runtime. Use this method to add services to the container.
		public void ConfigureServices(IServiceCollection services) {
			services.AddCors();
			
			services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_2);
			
			Mapper.Initialize(cfg => {
				cfg.CreateMap<User, User>();
				cfg.CreateMap<Order, Order>();
				cfg.CreateMap<CartItem, CartItem>();
				cfg.CreateMap<Item, Item>();
			});

			WebshopContext.SetConnectionString(Configuration.GetConnectionString("WebshopDatabase"));
			
			services.AddAuthentication("BasicAuthentication")
				.AddScheme<AuthenticationSchemeOptions, BasicAuthenticationHandler>("BasicAuthentication", null);

			services.AddScoped<IUserService, UserService>();
		}

		// This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
		public void Configure(IApplicationBuilder app, IHostingEnvironment env) {
			if (env.IsDevelopment()) {
				app.UseDeveloperExceptionPage();
			}

			app.UseAuthentication();
			
			app.UseCors(builder => builder
				.AllowAnyOrigin()
				.AllowAnyMethod()
				.AllowAnyHeader());
			
			app.UseMvc();
		}
	}
}