using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace WEService.Models
{
    public class User
    {
        [Key]
        public int userId { get; set; }
        public String email { get; set; }
        public String password { get; set; }
        //public List<Match> matches;

    }
}