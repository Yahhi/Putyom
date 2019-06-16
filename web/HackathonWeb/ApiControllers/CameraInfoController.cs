using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using HackathonWeb.Context.Models;
using HackathonWeb.Context;
using Microsoft.Azure.Storage;
using Microsoft.Azure.Storage.File;

namespace HackathonWeb.ApiControllers
{

    [Route("api/[controller]")]
    [ApiController]
    public class CameraInfoController : ControllerBase
    {
        private readonly PutemDbContext _context;

        public CameraInfoController(PutemDbContext context)
        {
            _context = context;
        }

        [HttpPost]
        public async Task<string> PostAsync([FromBody]CameraInfo cameraInfo)
        {
            if (ModelState.IsValid)
            {

                _context.Add(cameraInfo);
                await _context.SaveChangesAsync();
                return "ok";
            }
            return "error";
        }

        private string UploadToBlob() {
            // TableTest();
            CloudStorageAccount storageAccount = CloudStorageAccount.Parse(
     "connectionstring");
            CloudFileClient fileClient = storageAccount.CreateCloudFileClient();

            CloudFileShare share = fileClient.GetShareReference("fileshare");

            CloudFileDirectory rootDir = share.GetRootDirectoryReference();


            int size = 5 * 1024 * 1024;
            byte[] buffer = new byte[size];
            Random rand = new Random();
            rand.NextBytes(buffer);

            CloudFile file = rootDir.GetFileReference("Log3.txt");

            file.UploadFromByteArrayAsync(buffer, 0, buffer.Length);

            string result = "aa";

            //Begins an asynchronous operation to upload the contents of a byte array to a file. If the file already exists on the service, it will be overwritten.
            var res = file.BeginUploadFromByteArray(buffer, 0, buffer.Length, ProcessInformation, result);

            //Ends an asynchronous operation to upload the contents of a byte array to a file.
            //wait for the BeginUploadFromByteArray method execute completely then continue run the codes
            file.EndUploadFromByteArray(res);
            return "";
        }


        static void ProcessInformation(IAsyncResult result)
        {

            //The callback delegate that will receive notification when the asynchronous operation completes.
            string Name = (string)result.AsyncState;

            //this Name is aa
            Console.WriteLine(Name);

            Console.WriteLine("Complete");


        }

    }
}
