using Microsoft.WindowsAzure.Mobile.Service;

namespace WinningEdgeService.DataObjects
{
    public class UserItem : EntityData
    {
        public string Email { get; set; }

        public string Password { get; set; }
    }
}