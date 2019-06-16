using System;
using System.Collections.Generic;

namespace HackathonWeb.Context.Models
{
    public class Contractor
    {
        public int Id { get; set; }
        public string Name { get; set; }

        public List<Work> Works { get; set; }
    }
}
