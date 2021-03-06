import models.Music;
import org.junit.Assert;
import org.junit.Test;
import exception.YearException;
import java.util.Arrays;

public class MusicTest {

    private Music music ;


    @Test(expected = IllegalArgumentException.class)
    public void titleShouldNotBeNull() throws YearException {
        music = new Music(1, "genre1", "format", 2011, ("artist1"), null);
    }

    /*@Test(expected = IllegalArgumentException.class)
    public void fourArtistsShouldFail() throws YearException {
        music = new Music(1, "genre1", "format", 2011, Arrays.asList("artist1", "artist2", "artist3", "artist4"), "title2");
    }*/
   /* @Test
    public void threeArtistsShouldWork() throws YearException {
        music = new Music(1, "genre1", "format", 2011, Arrays.asList("artist1", "artist2", "artist3"), "title2");
        Assert.assertEquals(music.getArtist().size(), 3);
    }*/
    @Test(expected = IllegalArgumentException.class)
    public void negativeIdShouldFail() throws YearException {
        music = new Music(-1, "genre1", "format", 2011, ("artist1"), "title");
    }
    @Test
    public void positiveIdShouldWork() throws YearException {
        music = new Music(1, "genre1", "format", 2011, ("artist1"), "title");
        Assert.assertTrue(music.getId() > 0);
    }
    @Test(expected = YearException.class)
    public void musicYearGreaterThanCurrentYearShouldFail() throws YearException {
        music = new Music(1, "genre1", "format", 2020, ("artist1"), "title");
    }
}
