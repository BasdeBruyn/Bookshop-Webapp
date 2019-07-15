using Backend.Models;
using Microsoft.EntityFrameworkCore;

namespace Backend
{
    public class WebshopContext : DbContext {

        private static string _connectionString;
        
        public WebshopContext() {
        }

        public WebshopContext(DbContextOptions options)
            : base(options) {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder) {
            modelBuilder.HasAnnotation("ProductVersion", "2.2.4-servicing-10062");
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder) {
            if (!optionsBuilder.IsConfigured) {
                optionsBuilder.UseNpgsql(_connectionString);
            }
        }

        public static void SetConnectionString(string connectionString) {
            if (_connectionString != null)
                throw new System.InvalidOperationException("connection string is already set");
            
            _connectionString = connectionString;
        }

        public DbSet<User> Users { get; set; }
        public DbSet<Order> Orders { get; set; }
        public DbSet<Item> Items { get; set; }
        public DbSet<CartItem> CartItems { get; set; }
    }
}
