namespace WEService.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Initial : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Matches",
                c => new
                    {
                        matchId = c.Int(nullable: false, identity: true),
                        userId = c.Int(nullable: false),
                        matchTime = c.DateTime(nullable: false),
                        oppostion = c.String(),
                        oppGoals = c.Int(nullable: false),
                        oppPoints = c.Int(nullable: false),
                        shots = c.Int(nullable: false),
                        wides = c.Int(nullable: false),
                        shorts = c.Int(nullable: false),
                        fouls = c.Int(nullable: false),
                        scoresFromFouls = c.Int(nullable: false),
                        blocks = c.Int(nullable: false),
                        hooks = c.Int(nullable: false),
                        successfulPasses = c.Int(nullable: false),
                        unsuccessfulPasses = c.Int(nullable: false),
                        successfulHandPasses = c.Int(nullable: false),
                        unsuccessfulHandPasses = c.Int(nullable: false),
                        ownWon = c.Int(nullable: false),
                        ownLost = c.Int(nullable: false),
                        oppWon = c.Int(nullable: false),
                        oppLost = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.matchId);
            
            CreateTable(
                "dbo.Scores",
                c => new
                    {
                        scoreId = c.Int(nullable: false, identity: true),
                        goal = c.Boolean(nullable: false),
                        fromPlay = c.Boolean(nullable: false),
                        mins = c.Int(nullable: false),
                        secs = c.Int(nullable: false),
                        distance = c.Int(nullable: false),
                        matchId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.scoreId);
            
            CreateTable(
                "dbo.Users",
                c => new
                    {
                        userId = c.Int(nullable: false, identity: true),
                        email = c.String(),
                        password = c.String(),
                    })
                .PrimaryKey(t => t.userId);
            
        }
        
        public override void Down()
        {
            DropTable("dbo.Users");
            DropTable("dbo.Scores");
            DropTable("dbo.Matches");
        }
    }
}
