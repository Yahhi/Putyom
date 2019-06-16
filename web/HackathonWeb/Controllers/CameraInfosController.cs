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
    public class CameraInfosController : Controller
    {
        private readonly PutemDbContext _context;

        public CameraInfosController(PutemDbContext context)
        {
            _context = context;
        }

        // GET: CameraInfos
        public async Task<IActionResult> Index()
        {
            return View(await _context.CameraInfos.ToListAsync());
        }

        // GET: CameraInfos/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var cameraInfo = await _context.CameraInfos
                .FirstOrDefaultAsync(m => m.Id == id);
            if (cameraInfo == null)
            {
                return NotFound();
            }

            return View(cameraInfo);
        }

        // GET: CameraInfos/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: CameraInfos/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,Lat,Lng,Img,Rating")] CameraInfo cameraInfo)
        {
            if (ModelState.IsValid)
            {
                _context.Add(cameraInfo);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(cameraInfo);
        }

        // GET: CameraInfos/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var cameraInfo = await _context.CameraInfos.FindAsync(id);
            if (cameraInfo == null)
            {
                return NotFound();
            }
            return View(cameraInfo);
        }

        // POST: CameraInfos/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,Lat,Lng,Img,Rating")] CameraInfo cameraInfo)
        {
            if (id != cameraInfo.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(cameraInfo);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!CameraInfoExists(cameraInfo.Id))
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
            return View(cameraInfo);
        }

        // GET: CameraInfos/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var cameraInfo = await _context.CameraInfos
                .FirstOrDefaultAsync(m => m.Id == id);
            if (cameraInfo == null)
            {
                return NotFound();
            }

            return View(cameraInfo);
        }

        // POST: CameraInfos/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var cameraInfo = await _context.CameraInfos.FindAsync(id);
            _context.CameraInfos.Remove(cameraInfo);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool CameraInfoExists(int id)
        {
            return _context.CameraInfos.Any(e => e.Id == id);
        }
    }
}
