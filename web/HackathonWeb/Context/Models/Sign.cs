using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using Newtonsoft.Json;

namespace HackathonWeb.Context.Models
{
    public class Sign
    {
        public int Id { get; set; }

        public int Type { get; set; }

        public string DeviceId { get; set; }

        public List<SignInfo> SignInfos { get; set; }

        public int? WorkId { get; set; }
        [JsonIgnore]
        public Work Work { get; set; }

        [Column(TypeName = "decimal(8, 6)")]
        public decimal Lat { get; set; }

        [Column(TypeName = "decimal(8, 6)")]
        public decimal Lng { get; set; }
    }
}
