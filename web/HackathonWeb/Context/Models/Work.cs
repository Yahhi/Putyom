using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

namespace HackathonWeb.Context.Models
{
    public class Work
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public int ContractorId { get; set; }
        public Contractor Contractor { get; set; }

        public List<Sign> Signs { get; set; }

        public List<SignInfo> SignInfos { get; set; }

        public DateTime Created { get; set; }

        public DateTime PlannedStart { get; set; }
        public DateTime PlannedEnd { get; set; }
        public DateTime ActualStart { get; set; }
        public DateTime ActualEnd { get; set; }

        public List<CameraInfo> CameraInfos { get; set; }



        [Column(TypeName = "decimal(8, 6)")]
        public decimal PlannedStartLat { get; set; }

        [Column(TypeName = "decimal(8, 6)")]
        public decimal PlannedStartLng { get; set; }

        [Column(TypeName = "decimal(8, 6)")]
        public decimal PlannedEndLat { get; set; }

        [Column(TypeName = "decimal(8, 6)")]
        public decimal PlannedEndLng { get; set; }

        public int FinanceLocal { get; set; }
        public int FinanceFederal { get; set; }
        public int FinanceArea { get; set; }

        public string WorkDetails { get; set; }
    }
}
