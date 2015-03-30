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
        //public DateTime matchTime { get; set; }
        public String team { get; set; }
        public String oppostion { get; set; }
        public bool football { get; set; }
        // Variables for livescore
        public bool inPlay { get; set; }
        public bool halfTime { get; set; }
        public bool fullTime { get; set; }
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
        public Match()
        {

        }


        /*
        public Match(ClientMatch m)
        {
            userId = m.userId;
            matchId = m.matchId;
            team = m.team;
            oppostion = m.oppostion;
            football = m.football;
            inPlay = m.inPlay;
            halfTime = m.halfTime;
            fullTime = m.fullTime;
            oppGoals = m.oppGoals;
            oppPoints = m.oppPoints;
            shots = m.shots;
            wides = m.wides;
            shorts = m.shorts;
            fouls = m.fouls;
            scoresFromFouls = m.scoresFromFouls;
            blocks = m.blocks;
            hooks = m.hooks;
            successfulPasses = m.successfulPasses;
            unsuccessfulPasses = m.unsuccessfulPasses;
            successfulHandPasses = m.successfulHandPasses;
            unsuccessfulHandPasses = m.unsuccessfulHandPasses;
            ownWon = m.ownWon;
            ownLost = m.ownLost;
            oppWon = m.oppWon;
            oppLost = m.oppLost;

        }

         */
    }
}