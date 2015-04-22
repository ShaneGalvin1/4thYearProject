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
    [RoutePrefix("api/Scores")]
    public class ScoresController : ApiController
    {
        private WEServiceContext db = new WEServiceContext();

        // GET: api/Scores
        public IQueryable<Score> GetScores()
        {
            return db.Scores;
        }

        // GET: api/Scores/5
        [ResponseType(typeof(Score))]
        public async Task<IHttpActionResult> GetScore(int id)
        {
            Score score = await db.Scores.FindAsync(id);
            if (score == null)
            {
                return NotFound();
            }

            return Ok(score);
        }

        // GET: api/Scores/id
        [Route("id")]
        public int GetNextId()
        {
            return db.Scores.Count()+1;
        }
        // GET: api/Scores/analysis/1
        [ResponseType(typeof(List<int>))]
        [Route("analysis/{id:int}")]
        public List<int> GetAnalysis(int id)
        {
            List<int> scoreCount = new List<int>();
            int mId = 0;
            int scores1 = 0;
            int scores2 = 0;
            int scores3 = 0;
            int scores4 = 0;
            int scores5 = 0;
            int scores6 = 0;
            int scores7 = 0;
            
            foreach (var row in db.Matches)
            {
                if (row.userId == id)
                {
                    mId = row.matchId;
                }
            }
            foreach (var s in db.Scores)
            {
                if (s.matchId == mId)
                {
                    if(s.mins < 10)
                    {
                        scores1++;
                    }
                    else if(s.mins < 20 && s.mins >= 10)
                    {
                        scores2++;
                    }
                    else if (s.mins < 30 && s.mins >= 20)
                    {
                        scores3++;
                    }
                    else if (s.mins < 40 && s.mins >= 30)
                    {
                        scores4++;
                    }
                    else if (s.mins < 50 && s.mins >= 40)
                    {
                        scores5++;
                    }
                    else if (s.mins < 60 && s.mins >= 50)
                    {
                        scores6++;
                    }
                    else
                    {
                        scores7++;
                    }
                }
            }
            scoreCount.Add(scores1);
            scoreCount.Add(scores2);
            scoreCount.Add(scores3);
            scoreCount.Add(scores4);
            scoreCount.Add(scores5);
            scoreCount.Add(scores6);
            scoreCount.Add(scores7);
            return scoreCount;
        }

        // PUT: api/Scores/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutScore(int id, Score score)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != score.scoreId)
            {
                return BadRequest();
            }

            db.Entry(score).State = EntityState.Modified;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ScoreExists(id))
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

        // POST: api/Scores
        [ResponseType(typeof(Score))]
        public async Task<IHttpActionResult> PostScore(Score score)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Scores.Add(score);
            await db.SaveChangesAsync();

            return CreatedAtRoute("DefaultApi", new { id = score.scoreId }, score);
        }

        // DELETE: api/Scores/5
        [ResponseType(typeof(Score))]
        public async Task<IHttpActionResult> DeleteScore(int id)
        {
            Score score = await db.Scores.FindAsync(id);
            if (score == null)
            {
                return NotFound();
            }

            db.Scores.Remove(score);
            await db.SaveChangesAsync();

            return Ok(score);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool ScoreExists(int id)
        {
            return db.Scores.Count(e => e.scoreId == id) > 0;
        }
    }
}