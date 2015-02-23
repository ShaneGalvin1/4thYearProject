using System.Linq;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Controllers;
using System.Web.Http.OData;
using Microsoft.WindowsAzure.Mobile.Service;
using WinningEdgeService.DataObjects;
using WinningEdgeService.Models;

namespace WinningEdgeService.Controllers
{
    public class UserItemController : TableController<UserItem>
    {
        protected override void Initialize(HttpControllerContext controllerContext)
        {
            base.Initialize(controllerContext);
            WinningEdgeContext context = new WinningEdgeContext();
            DomainManager = new EntityDomainManager<UserItem>(context, Request, Services);
        }

        // GET tables/TodoItem
        public IQueryable<UserItem> GetAllUserItems()
        {
            return Query();
        }

        // GET tables/TodoItem/48D68C86-6EA6-4C25-AA33-223FC9A27959
        public SingleResult<UserItem> GetUserItem(string id)
        {
            return Lookup(id);
        }

        // PATCH tables/TodoItem/48D68C86-6EA6-4C25-AA33-223FC9A27959
        public Task<UserItem> PatchUserItem(string id, Delta<UserItem> patch)
        {
            return UpdateAsync(id, patch);
        }

        // POST tables/TodoItem
        public async Task<IHttpActionResult> PostUserItem(UserItem item)
        {
            UserItem current = await InsertAsync(item);
            return CreatedAtRoute("Tables", new { id = current.Id }, current);
        }

        // DELETE tables/TodoItem/48D68C86-6EA6-4C25-AA33-223FC9A27959
        public Task DeleteUserItem(string id)
        {
            return DeleteAsync(id);
        }
    }
}