using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using HackathonWeb.Context;
using HackathonWeb.Context.Models;

namespace HackathonWeb.Controllers
{
    public class SignsController : Controller
    {
        private readonly PutemDbContext _context;

        public SignsController(PutemDbContext context)
        {
            _context = context;
        }

        // GET: Signs
        public async Task<IActionResult> Index()
        {
            return View(await _context.Sign.ToListAsync());
        }

        // GET: Signs/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var sign = await _context.Sign
                .FirstOrDefaultAsync(m => m.Id == id);
            if (sign == null)
            {
                return NotFound();
            }

            return View(sign);
        }

        // GET: Signs/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Signs/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,Type,DeviceId")] Sign sign)
        {
            if (ModelState.IsValid)
            {
                _context.Add(sign);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(sign);
        }

        // GET: Signs/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var sign = await _context.Sign.FindAsync(id);
            if (sign == null)
            {
                return NotFound();
            }
            return View(sign);
        }

        // POST: Signs/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,Type,DeviceId")] Sign sign)
        {
            if (id != sign.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(sign);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!SignExists(sign.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(sign);
        }

        // GET: Signs/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var sign = await _context.Sign
                .FirstOrDefaultAsync(m => m.Id == id);
            if (sign == null)
            {
                return NotFound();
            }

            return View(sign);
        }

        // POST: Signs/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var sign = await _context.Sign.FindAsync(id);
            _context.Sign.Remove(sign);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool SignExists(int id)
        {
            return _context.Sign.Any(e => e.Id == id);
        }
    }
}
