namespace WEService.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Initial : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Matches", "matchDate", c => c.Long(nullable: false));
            DropColumn("dbo.Matches", "matchTime");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Matches", "matchTime", c => c.DateTime(nullable: false));
            DropColumn("dbo.Matches", "matchDate");
        }
    }
}
