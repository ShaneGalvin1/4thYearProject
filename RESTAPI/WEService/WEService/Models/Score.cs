using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace WEService.Models
{
    public class Score
    {
        [Key]
        public int scoreId { get; set; }
        public bool goal { get; set; }
        public bool fromPlay { get; set; }
        public int mins { get; set; }
        public int secs { get; set; }
        public int distance { get; set; }

        // Foreign Key
        public int matchId { get; set; }
        public Score()
        {

        }
        public Score(int id, bool g, bool p, int mins, int secs, int dis)
        {
            scoreId = id;
            goal = g;
            fromPlay = p;
            this.mins = mins;
            this.secs = secs;
            distance = dis;
        }
    }
}