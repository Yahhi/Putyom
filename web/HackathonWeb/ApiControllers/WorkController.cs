using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using HackathonWeb.Context;
using HackathonWeb.Context.Models;
using HackathonWeb.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace HackathonWeb.ApiControllers
{

    [Route("api/[controller]")]
    [ApiController]
    public class WorkController : ControllerBase
    {
        private readonly PutemDbContext _context;

        public WorkController(PutemDbContext context)
        {
            _context = context;
        }

        [HttpGet]
        public async Task<List<Work>> GetAsync()
        {

            return await _context.Work.Include(i => i.Signs).ToListAsync();
        }

    }
}
