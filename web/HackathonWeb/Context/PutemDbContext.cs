using System;
using HackathonWeb.Context.Models;
using Microsoft.EntityFrameworkCore;

namespace HackathonWeb.Context
{
    public class PutemDbContext : DbContext
    {
        public PutemDbContext(DbContextOptions<PutemDbContext> options)
            : base(options)
        { }

        public DbSet<SignInfo> SignInfos { get; set; }
        public DbSet<Contractor> Contractors { get; set; }
        public DbSet<CameraInfo> CameraInfos { get; set; }
        public DbSet<HackathonWeb.Context.Models.Work> Work { get; set; }
        public DbSet<HackathonWeb.Context.Models.Sign> Sign { get; set; }
    }
}
