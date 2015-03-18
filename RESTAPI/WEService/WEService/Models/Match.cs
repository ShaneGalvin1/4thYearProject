using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace WEService.Models
{
    public class Match
    {
        // Foreign Key
        public int userId { get; set; }
        // Match Info
        [Key]
        public int matchId { get; set; }
        public DateTime matchTime { get; set; }
        public String oppostion { get; set; }

        // Scores
        public List<Score> scores;
        // Opposition Scores
        public int oppGoals { get; set; }
        public int oppPoints { get; set; }
        // Shooting
        public int shots { get; set; }
        public int wides { get; set; }
        public int shorts { get; set; }
        // Tackling
        public int fouls { get; set; }
        public int scoresFromFouls { get; set; }
        public int blocks { get; set; }
        public int hooks { get; set; }
        // Passing
        public int successfulPasses { get; set; }
        public int unsuccessfulPasses { get; set; }
        public int successfulHandPasses { get; set; }
        public int unsuccessfulHandPasses { get; set; }
        // Restarts (kickouts/puckouts)
        public int ownWon { get; set; }
        public int ownLost { get; set; }
        public int oppWon { get; set; }
        public int oppLost { get; set; }
    }
}