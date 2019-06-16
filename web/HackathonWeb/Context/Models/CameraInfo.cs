using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace HackathonWeb.Context.Models
{
    public class CameraInfo
    {
        public int Id { get; set; }

        [Column(TypeName = "decimal(8, 6)")]
        public decimal Lat { get; set; }

        [Column(TypeName = "decimal(8, 6)")]
        public decimal Lng { get; set; }

        public byte[] Img { get; set; }

        public int Rating { get; set; }

        public int WorkId { get; set; }
        public Work Work { get; set; }
    }
}
