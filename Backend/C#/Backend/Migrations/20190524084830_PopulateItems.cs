using System;
using System.IO;
using Microsoft.EntityFrameworkCore.Migrations;

namespace Backend.Migrations
{
    public partial class PopulateItems : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder) {
            string sqlFile = Path.Combine(Directory.GetCurrentDirectory(), "item_inserts.sql");
            migrationBuilder.Sql(File.ReadAllText(sqlFile));
        }

        protected override void Down(MigrationBuilder migrationBuilder) {
            migrationBuilder.Sql("TRUNCATE TABLE Items");
        }
    }
}
