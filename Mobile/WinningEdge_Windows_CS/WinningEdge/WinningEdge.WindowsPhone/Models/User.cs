using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.WindowsAzure.MobileServices;
using Newtonsoft.Json;

namespace WinningEdgeService.Models
{
    public class User
    {
        
        private int Id { get; set; }
        [JsonProperty("email")]
        private String Email { get; set; }
        [JsonProperty("password")]
        private String Password { get; set; }

        public User(String e, String p)
        {
            Email = e;
            Password = p;
        }
    }
}