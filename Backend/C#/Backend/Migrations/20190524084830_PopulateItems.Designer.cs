﻿// <auto-generated />
using System;
using Backend;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

namespace Backend.Migrations
{
    [DbContext(typeof(WebshopContext))]
    [Migration("20190524084830_PopulateItems")]
    partial class PopulateItems
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.SerialColumn)
                .HasAnnotation("ProductVersion", "2.2.4-servicing-10062")
                .HasAnnotation("Relational:MaxIdentifierLength", 63);

            modelBuilder.Entity("Backend.Models.Item", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<bool>("Available");

                    b.Property<string>("Description")
                        .IsRequired()
                        .HasMaxLength(4095);

                    b.Property<string>("Name")
                        .IsRequired()
                        .HasMaxLength(127);

                    b.Property<int>("Price");

                    b.Property<string>("Url")
                        .IsRequired()
                        .HasMaxLength(511);

                    b.HasKey("Id");

                    b.ToTable("Items");
                });

            modelBuilder.Entity("Backend.Models.Order", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("CurDate");

                    b.Property<string>("FirstName")
                        .IsRequired()
                        .HasMaxLength(63);

                    b.Property<string>("HouseNr")
                        .IsRequired()
                        .HasMaxLength(5);

                    b.Property<string>("LastName")
                        .IsRequired()
                        .HasMaxLength(63);

                    b.Property<string>("PhoneNr")
                        .IsRequired()
                        .HasMaxLength(12);

                    b.Property<string>("PostalCode")
                        .IsRequired()
                        .HasMaxLength(7);

                    b.Property<string>("Residence")
                        .IsRequired()
                        .HasMaxLength(63);

                    b.Property<string>("StreetName")
                        .IsRequired()
                        .HasMaxLength(63);

                    b.Property<int>("TotalPrice");

                    b.Property<int>("UserId");

                    b.HasKey("Id");

                    b.HasIndex("UserId");

                    b.ToTable("Orders");
                });

            modelBuilder.Entity("Backend.Models.User", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<bool>("Admin");

                    b.Property<string>("Email")
                        .IsRequired()
                        .HasMaxLength(127);

                    b.Property<string>("Password")
                        .IsRequired()
                        .HasMaxLength(255);

                    b.HasKey("Id");

                    b.ToTable("Users");
                });

            modelBuilder.Entity("Backend.Models.Order", b =>
                {
                    b.HasOne("Backend.Models.User", "User")
                        .WithMany()
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade);
                });
#pragma warning restore 612, 618
        }
    }
}
