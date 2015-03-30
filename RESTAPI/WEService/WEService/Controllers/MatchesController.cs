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
using System.Web.Http.Description;
using WEService.Models;

namespace WEService.Controllers
{
    [RoutePrefix("api/Matches")]
    public class MatchesController : ApiController
    {
        private WEServiceContext db = new WEServiceContext();

        // GET: api/Matches
        public IQueryable<Match> GetMatches()
        {
            return db.Matches;
        }

        // GET: api/Matches/5
        [ResponseType(typeof(Match))]
        public async Task<IHttpActionResult> GetMatch(int id)
        {
            Match match = await db.Matches.FindAsync(id);
            if (match == null)
            {
                return NotFound();
            }

            return Ok(match);
        }

        // GET
        [ResponseType(typeof(String))]
        [Route("Name/{id:int}")]
        public async Task<IHttpActionResult> GetTeam(int id)
        {
            Match match = await db.Matches.FindAsync(id);
            if (match == null)
            {
                return NotFound();
            }
            String s = match.oppostion;
            return Ok(s);
        }
        /*
        // GET
        [Route("live")]
        public List<String> GetLive()       
        {
            List<String> list = new List<string>();
            for(int i = 0; i < db.Matches.Count(); i++)
            {
                if(db.Matches.ElementAt(i).matchTime.Date == DateTime.Today.Date)
                {
                    list.Add(db.Matches.ElementAt(i).team + " vs. " + db.Matches.ElementAt(i).oppostion);
                }
            }
            return list;
        }
        */

        // PUT: api/Matches/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutMatch(int id, Match match)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != match.matchId)
            {
                return BadRequest();
            }

            db.Entry(match).State = EntityState.Modified;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!MatchExists(id))
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

        // POST: api/Matches
        [ResponseType(typeof(Match))]
        public async Task<IHttpActionResult> PostMatch(Match match)
        {
            //match.matchTime = DateTime.Now;
            
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Matches.Add(match);
            await db.SaveChangesAsync();

            return CreatedAtRoute("DefaultApi", new { id = match.matchId }, match);
        }

       


        // DELETE: api/Matches/5
        [ResponseType(typeof(Match))]
        public async Task<IHttpActionResult> DeleteMatch(int id)
        {
            Match match = await db.Matches.FindAsync(id);
            if (match == null)
            {
                return NotFound();
            }

            db.Matches.Remove(match);
            await db.SaveChangesAsync();

            return Ok(match);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool MatchExists(int id)
        {
            return db.Matches.Count(e => e.matchId == id) > 0;
        }
    }
}