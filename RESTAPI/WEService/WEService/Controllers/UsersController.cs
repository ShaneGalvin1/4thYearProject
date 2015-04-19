using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Cors;
using System.Web.Http.Description;
using WEService.Models;

namespace WEService.Controllers
{
    //[EnableCors(origins: "http://weservice.azurewebsites.net", headers: "*", methods: "*")]
    [RoutePrefix("api/Users")]
    public class UsersController : ApiController
    {
        private WEServiceContext db = new WEServiceContext();

        // GET: api/Users
        public IQueryable<User> GetUsers()
        {
            return db.Users;
        }

        // GET: api/Users/5
        [ResponseType(typeof(User))]
        public async Task<IHttpActionResult> GetUser(int id)
        {
            User user = await db.Users.FindAsync(id);
            if (user == null)
            {
                return NotFound();
            }

            return Ok(user);
        }
        // POST: api/Users/check
        [Route("check")]
        [ResponseType(typeof(String))]
        public String CheckUser(User u)
        {
            foreach (var row in db.Users)
            {
                if (row.email.Equals(u.email))
                {
                    return "Exists";
                }
            }
            return "Available";

        }

        // GET: api/Users/login
        [Route("login")]
        [ResponseType(typeof(User))]
        public User PostLogin(User u)
        {
            //
            // Encryption for password check
            //
            byte[] data = System.Text.Encoding.ASCII.GetBytes(u.password);
            data = new System.Security.Cryptography.SHA256Managed().ComputeHash(data);
            String hash = System.Text.Encoding.ASCII.GetString(data);
            u.password = hash;
            
            User user = new User();
            user.userId = 0;
            user.email = "";
            user.password = "";
            foreach (var row in db.Users)
            {
                if (row.email.Equals(u.email))
                {
                    if (row.password.Equals(u.password))
                    {
                        user.email = row.email;
                        user.password = row.password;
                        user.userId = row.userId;
                        return user;
                    }
                    user.email = "ok";
                }
            }            
            return user;
        }

        // PUT: api/Users/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutUser(int id, User user)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != user.userId)
            {
                return BadRequest();
            }

            db.Entry(user).State = EntityState.Modified;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }


        

        // Used for Register
        // POST: api/Users
        [ResponseType(typeof(User))]
        public async Task<IHttpActionResult> PostUser(User user)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            // Check if email exists
            foreach (var row in db.Users)
            {
                if(row.email.Equals(user.email))
                {
                    return BadRequest("Email already exists");
                }
            }
            // Encryption for password
            
            byte[] data = System.Text.Encoding.ASCII.GetBytes(user.password);
            data = new System.Security.Cryptography.SHA256Managed().ComputeHash(data);
            String hash = System.Text.Encoding.ASCII.GetString(data);
            user.password = hash;
            
            db.Users.Add(user);
            await db.SaveChangesAsync();

            return CreatedAtRoute("DefaultApi", new { id = user.userId }, user);
        }

        // DELETE: api/Users/5
        [ResponseType(typeof(User))]
        public async Task<IHttpActionResult> DeleteUser(int id)
        {
            User user = await db.Users.FindAsync(id);
            if (user == null)
            {
                return NotFound();
            }

            db.Users.Remove(user);
            await db.SaveChangesAsync();

            return Ok(user);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool UserExists(int id)
        {
            return db.Users.Count(e => e.userId == id) > 0;
        }
    }
}