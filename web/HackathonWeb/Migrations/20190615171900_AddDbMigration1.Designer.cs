﻿// <auto-generated />
using System;
using HackathonWeb.Context;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace HackathonWeb.Migrations
{
    [DbContext(typeof(PutemDbContext))]
    [Migration("20190615171900_AddDbMigration1")]
    partial class AddDbMigration1
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.2.4-servicing-10062")
                .HasAnnotation("Relational:MaxIdentifierLength", 128)
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("HackathonWeb.Context.Models.CameraInfo", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<byte[]>("Img");

                    b.Property<decimal>("Lat")
                        .HasColumnType("decimal(8, 6)");

                    b.Property<decimal>("Lng")
                        .HasColumnType("decimal(8, 6)");

                    b.Property<int>("Rating");

                    b.Property<int>("WorkId");

                    b.HasKey("Id");

                    b.HasIndex("WorkId");

                    b.ToTable("CameraInfos");
                });

            modelBuilder.Entity("HackathonWeb.Context.Models.Contractor", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("Name");

                    b.HasKey("Id");

                    b.ToTable("Contractors");
                });

            modelBuilder.Entity("HackathonWeb.Context.Models.Sign", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("DeviceId");

                    b.Property<int>("Type");

                    b.Property<int?>("WorkId");

                    b.HasKey("Id");

                    b.HasIndex("WorkId");

                    b.ToTable("Sign");
                });

            modelBuilder.Entity("HackathonWeb.Context.Models.SignInfo", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<DateTime>("Created");

                    b.Property<decimal>("Lat")
                        .HasColumnType("decimal(8, 6)");

                    b.Property<decimal>("Lng")
                        .HasColumnType("decimal(8, 6)");

                    b.Property<int>("SignId");

                    b.Property<int>("Type");

                    b.Property<int?>("WorkId");

                    b.HasKey("Id");

                    b.HasIndex("SignId");

                    b.HasIndex("WorkId");

                    b.ToTable("SignInfos");
                });

            modelBuilder.Entity("HackathonWeb.Context.Models.Work", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<DateTime>("ActualEnd");

                    b.Property<DateTime>("ActualStart");

                    b.Property<int>("ContractorId");

                    b.Property<DateTime>("Created");

                    b.Property<string>("Name");

                    b.Property<DateTime>("PlannedEnd");

                    b.Property<DateTime>("PlannedStart");

                    b.HasKey("Id");

                    b.HasIndex("ContractorId");

                    b.ToTable("Work");
                });

            modelBuilder.Entity("HackathonWeb.Context.Models.CameraInfo", b =>
                {
                    b.HasOne("HackathonWeb.Context.Models.Work", "Work")
                        .WithMany("CameraInfos")
                        .HasForeignKey("WorkId")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("HackathonWeb.Context.Models.Sign", b =>
                {
                    b.HasOne("HackathonWeb.Context.Models.Work", "Work")
                        .WithMany("Signs")
                        .HasForeignKey("WorkId");
                });

            modelBuilder.Entity("HackathonWeb.Context.Models.SignInfo", b =>
                {
                    b.HasOne("HackathonWeb.Context.Models.Sign", "Sign")
                        .WithMany("SignInfos")
                        .HasForeignKey("SignId")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("HackathonWeb.Context.Models.Work", "Work")
                        .WithMany("SignInfos")
                        .HasForeignKey("WorkId");
                });

            modelBuilder.Entity("HackathonWeb.Context.Models.Work", b =>
                {
                    b.HasOne("HackathonWeb.Context.Models.Contractor", "Contractor")
                        .WithMany("Works")
                        .HasForeignKey("ContractorId")
                        .OnDelete(DeleteBehavior.Cascade);
                });
#pragma warning restore 612, 618
        }
    }
}
