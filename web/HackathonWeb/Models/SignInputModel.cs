using System;
namespace HackathonWeb.Models
{
    public class SignInputModel
    {
        public decimal Lat { get; set; }
        public decimal Lng { get; set; }
        public int Type { get; set; }
        public string Id { get; set; }
    }
}
