namespace WEService.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class initial : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Matches", "home", c => c.Boolean(nullable: false));
            AddColumn("dbo.Matches", "yellows", c => c.Int(nullable: false));
            AddColumn("dbo.Matches", "reds", c => c.Int(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Matches", "reds");
            DropColumn("dbo.Matches", "yellows");
            DropColumn("dbo.Matches", "home");
        }
    }
}
