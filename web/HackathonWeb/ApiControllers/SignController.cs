using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using HackathonWeb.Context;
using HackathonWeb.Context.Models;
using HackathonWeb.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace HackathonWeb.ApiControllers
{

    [Route("api/[controller]")]
    [ApiController]
    public class SignController : ControllerBase
    {
        private readonly PutemDbContext _context;

        public SignController(PutemDbContext context)
        {
            _context = context;
        }

        [HttpGet]
        public async Task<List<Sign>> GetAsync()
        {
            var signs = await _context.Sign.Include(i=>i.Work).ToListAsync();

            return signs.ToList();
        }

        [HttpGet("bywork")]
        public async Task<List<Sign>> GetAsync(int id)
        {
            return await _context.Sign.Where(i=>i.WorkId == id).ToListAsync();
        }


        [HttpGet("create")]
        public async Task<string> PostAsync([FromQuery]SignInfo signInfo)
        {
            if (ModelState.IsValid)
            {
                var signId = signInfo.SignId;
                var sign = await _context.Sign.FirstOrDefaultAsync(i=>i.Id == signId);
                var workId = sign.WorkId;
                signInfo.WorkId = workId;
                signInfo.Created = DateTime.Now;
                sign.Lat = signInfo.Lat;
                sign.Lng = signInfo.Lng;

                _context.Add(signInfo);
                await _context.SaveChangesAsync();
                return "ok";
            }
            return "error";
        }

        [HttpPost("activate")]
        public async Task<string> ActivateAsync([FromBody]SignActivate signActivate)
        {
            if (ModelState.IsValid)
            {
                var sign = await _context.Sign.FirstAsync(i => i.Id == signActivate.SignId);
                var work = await _context.Work.FirstAsync(i => i.Id == signActivate.WorkId);
                sign.WorkId = work.Id;
                work.ActualStart = DateTime.Now;

                _context.Sign.Update(sign);
                _context.Work.Update(work);
                await _context.SaveChangesAsync();
                return "ok";
            }
            return "error";
        }

        [HttpPost("deactivate")]
        public async Task<string> DeactivateAsync([FromBody]SignActivate signDeactivate)
        {
            if (ModelState.IsValid)
            {
                var sign = await _context.Sign.FirstAsync(i => i.Id == signDeactivate.SignId);
                var work = await _context.Work.FirstAsync(i => i.Id == sign.Id);
                sign.WorkId = null;
                work.ActualEnd = DateTime.Now;

                _context.Sign.Update(sign);
                _context.Work.Update(work);
                await _context.SaveChangesAsync();
                return "ok";
            }
            return "error";
        }

    }
}
