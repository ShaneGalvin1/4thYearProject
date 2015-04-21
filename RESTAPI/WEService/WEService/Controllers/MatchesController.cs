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

        // GET: api/Matches/Name/4
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
        
        // GET: api/Matches/live/football
        [ResponseType(typeof(List<String>))]
        [Route("live/football")]
        public List<String> GetLiveFootball()       
        {

            
            List<String> list = new List<string>();
            List<Match> mList = new List<Match>();
            foreach(var row in db.Matches)
            {
                mList.Add(row);
            }

            foreach(Match m in mList)
            {
                DateTime start = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);
                long l = m.matchDate;
                DateTime date = start.AddMilliseconds(l);
                DateTime today = DateTime.Today;
                if (date.Date == today.Date && m.football == true)
                {
                    if (m.fullTime == false)
                    {
                        int g = 0;
                        int p = 0;
                        foreach (var s in db.Scores)
                        {
                            if (s.matchId == m.matchId)
                            {
                                if (s.goal == true)
                                {
                                    g++;
                                }
                                else
                                {
                                    p++;
                                }
                            }
                        }
                        if (!m.home)
                        {
                            list.Add(m.oppostion + " " + m.oppGoals + " - " + m.oppPoints + " vs. " + g + " - " + p + " " + m.team);
                        }
                        else
                        {
                            list.Add(m.team + " " + g + " - " + p + " vs. " + m.oppGoals + " - " + m.oppPoints + " " + m.oppostion);
                        }
                    }
                }
            }
            if(!list.Any())
            {
                list.Add("No matches currently being played");
            }
            return list;
        }

        // GET: api/Matches/live/hurling
        [ResponseType(typeof(List<String>))]
        [Route("live/hurling")]
        public List<String> GetLiveHurling()
        {


            List<String> list = new List<string>();
            List<Match> mList = new List<Match>();
            foreach (var row in db.Matches)
            {
                mList.Add(row);
            }

            foreach (Match m in mList)
            {
                DateTime start = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);
                long l = m.matchDate;
                DateTime date = start.AddMilliseconds(l);
                DateTime today = DateTime.Today;
                if (date.Date == today.Date && m.football == false)
                {
                    if(m.fullTime == false)
                    {
                        int g = 0;
                        int p = 0;
                        foreach (var s in db.Scores)
                        {
                            if (s.matchId == m.matchId)
                            {
                                if (s.goal == true)
                                {
                                    g++;
                                }
                                else
                                {
                                    p++;
                                }
                            }
                        }
                        if (!m.home)
                        {
                            list.Add(m.oppostion + " " + m.oppGoals + " - " + m.oppPoints + " vs. " + g + " - " + p + " " + m.team);
                        }
                        else
                        {
                            list.Add(m.team + " " + g + " - " + p + " vs. " + m.oppGoals + " - " + m.oppPoints + " " + m.oppostion);
                        }
                    }
                }
            }
            if (!list.Any())
            {
                list.Add("No matches currently being played");
            }
            return list;
        }
 
        // Results
        // GET: api/Matches/results/hurling
        [ResponseType(typeof(List<String>))]
        [Route("results/hurling")]
        public List<String> GetResultHurling()
        {


            List<String> list = new List<string>();
            List<Match> mList = new List<Match>();
            foreach (var row in db.Matches)
            {
                mList.Add(row);
            }

            foreach (Match m in mList)
            {
                DateTime start = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);
                long l = m.matchDate;
                DateTime date = start.AddMilliseconds(l);
                DateTime today = DateTime.Today;
                if (date.Date == today.Date && m.football == false)
                {
                    if (m.fullTime == true)
                    {
                        int g = 0;
                        int p = 0;
                        foreach (var s in db.Scores)
                        {
                            if (s.matchId == m.matchId)
                            {
                                if (s.goal == true)
                                {
                                    g++;
                                }
                                else
                                {
                                    p++;
                                }
                            }
                        }
                        if (!m.home)
                        {
                            list.Add(m.oppostion + " " + m.oppGoals + " - " + m.oppPoints + " vs. " + g + " - " + p + " " + m.team);
                        }
                        else
                        {
                            list.Add(m.team + " " + g + " - " + p + " vs. " + m.oppGoals + " - " + m.oppPoints + " " + m.oppostion);
                        }
                    }
                }
            }
            if (!list.Any())
            {
                list.Add("No match results");
            }
            return list;
        }

        // Results
        // GET: api/Matches/results/hurling
        [ResponseType(typeof(List<String>))]
        [Route("results/football")]
        public List<String> GetResultFootball()
        {


            List<String> list = new List<string>();
            List<Match> mList = new List<Match>();
            foreach (var row in db.Matches)
            {
                mList.Add(row);
            }

            foreach (Match m in mList)
            {
                DateTime start = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);
                long l = m.matchDate;
                DateTime date = start.AddMilliseconds(l);
                DateTime today = DateTime.Today;
                if (date.Date == today.Date && m.football == true)
                {
                    if (m.fullTime == true)
                    {
                        int g = 0;
                        int p = 0;
                        foreach (var s in db.Scores)
                        {
                            if (s.matchId == m.matchId)
                            {
                                if (s.goal == true)
                                {
                                    g++;
                                }
                                else
                                {
                                    p++;
                                }
                            }
                        }
                        if(!m.home)
                        {
                            list.Add(m.oppostion + " " + m.oppGoals + " - " + m.oppPoints + " vs. " + g + " - " + p + " " + m.team);
                        }
                        else
                        {
                            list.Add(m.team + " " + g + " - " + p + " vs. " + m.oppGoals + " - " + m.oppPoints + " " + m.oppostion);
                        }
                        
                    }
                }
            }
            if (!list.Any())
            {
                list.Add("No match results");
            }
            return list;
        }

        
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
            //long time = match.matchTime.Ticks;
            //DateTime date = new DateTime(time);
            //match.matchTime = DateTime.Today;
            
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