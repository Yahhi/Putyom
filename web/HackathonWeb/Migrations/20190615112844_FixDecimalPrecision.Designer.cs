﻿// <auto-generated />
using HackathonWeb.Context;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace HackathonWeb.Migrations
{
    [DbContext(typeof(PutemDbContext))]
    [Migration("20190615112844_FixDecimalPrecision")]
    partial class FixDecimalPrecision
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.2.4-servicing-10062")
                .HasAnnotation("Relational:MaxIdentifierLength", 128)
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("HackathonWeb.Context.Models.Contractor", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("Name");

                    b.HasKey("Id");

                    b.ToTable("Contractors");
                });

            modelBuilder.Entity("HackathonWeb.Context.Models.SignInfo", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("DeviceId");

                    b.Property<decimal>("Lat")
                        .HasColumnType("decimal(8, 6)");

                    b.Property<decimal>("Lng")
                        .HasColumnType("decimal(8, 6)");

                    b.Property<int>("Type");

                    b.HasKey("Id");

                    b.ToTable("SignInfos");
                });
#pragma warning restore 612, 618
        }
    }
}
