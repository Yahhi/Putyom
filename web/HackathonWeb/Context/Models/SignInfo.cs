using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace HackathonWeb.Context.Models
{
    public class SignInfo
    {
        public int Id { get; set; }

        [Column(TypeName = "decimal(8, 6)")]
        public decimal Lat { get; set; }

        [Column(TypeName = "decimal(8, 6)")]
        public decimal Lng { get; set; }
        public int Type { get; set; }

        public int SignId { get; set; }
        public Sign Sign { get; set; }

        public DateTime Created { get; set; }

        public int? WorkId { get; set; }
        public Work Work { get; set; }
    }
}
