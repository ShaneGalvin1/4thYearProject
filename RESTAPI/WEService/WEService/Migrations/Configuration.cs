namespace WEService.Migrations
{
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Migrations;
    using System.Linq;
    using WEService.Models;

    internal sealed class Configuration : DbMigrationsConfiguration<WEService.Models.WEServiceContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
        }

        protected override void Seed(WEService.Models.WEServiceContext context)
        {
            context.Users.AddOrUpdate(x => x.userId,
        new User() { userId = 1, email = "email@gmail.com", password = "password" },
        new User() { userId = 2, email = "example@gmail.com", password = "password" }
        );

            context.Matches.AddOrUpdate(x => x.matchId,
                new Match()
                {
                    matchId = 1,
                    matchTime = new DateTime(2014, 03, 02, 10, 30, 52),
                    oppostion = "Team 1",
                    oppGoals = 1,
                    oppPoints = 13,
                    shots = 30,
                    wides = 9,
                    shorts = 3,
                    fouls = 10,
                    scoresFromFouls = 7,
                    blocks = 3,
                    hooks = 4,
                    successfulPasses = 47,
                    unsuccessfulPasses = 24,
                    successfulHandPasses = 16,
                    unsuccessfulHandPasses = 5,
                    ownWon = 7,
                    ownLost = 4,
                    oppWon = 3,
                    oppLost = 6,
                    userId = 1
                },
                new Match()
                {
                    matchId = 2,
                    matchTime = new DateTime(2014, 03, 02, 10, 30, 52),
                    oppostion = "Team 2",
                    oppGoals = 1,
                    oppPoints = 13,
                    shots = 30,
                    wides = 9,
                    shorts = 3,
                    fouls = 10,
                    scoresFromFouls = 7,
                    blocks = 3,
                    hooks = 4,
                    successfulPasses = 47,
                    unsuccessfulPasses = 24,
                    successfulHandPasses = 16,
                    unsuccessfulHandPasses = 5,
                    ownWon = 7,
                    ownLost = 4,
                    oppWon = 3,
                    oppLost = 6,
                    userId = 1
                },
                new Match()
                {
                    matchId = 3,
                    matchTime = new DateTime(2014, 03, 02, 10, 30, 52),
                    oppostion = "Team 3",
                    oppGoals = 1,
                    oppPoints = 13,
                    shots = 30,
                    wides = 9,
                    shorts = 3,
                    fouls = 10,
                    scoresFromFouls = 7,
                    blocks = 3,
                    hooks = 4,
                    successfulPasses = 47,
                    unsuccessfulPasses = 24,
                    successfulHandPasses = 16,
                    unsuccessfulHandPasses = 5,
                    ownWon = 7,
                    ownLost = 4,
                    oppWon = 3,
                    oppLost = 6,
                    userId = 2
                }
            );

            context.Scores.AddOrUpdate(x => x.scoreId,
                new Score()
                {
                    scoreId = 1,
                    goal = false,
                    fromPlay = true,
                    mins = 2,
                    secs = 43,
                    distance = 21,
                    matchId = 1
                },
                new Score()
                {
                    scoreId = 2,
                    goal = false,
                    fromPlay = false,
                    mins = 6,
                    secs = 45,
                    distance = 45,
                    matchId = 1
                },
                new Score()
                {
                    scoreId = 3,
                    goal = true,
                    fromPlay = true,
                    mins = 2,
                    secs = 43,
                    distance = 21,
                    matchId = 2
                },
                new Score()
                {
                    scoreId = 4,
                    goal = false,
                    fromPlay = true,
                    mins = 2,
                    secs = 43,
                    distance = 21,
                    matchId = 3
                }
            );
        }
    }
}
