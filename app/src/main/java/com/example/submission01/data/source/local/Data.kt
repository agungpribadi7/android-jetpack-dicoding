package com.example.submission01.data.source.local

import com.example.submission01.data.DataMovieResponse
import com.example.submission01.data.GenresItem as MovieGenresItem
import com.example.submission01.data.ProductionCompaniesItem as MovieProductionCompaniesItem
import com.example.submission01.data.source.remote.response.DataTvResponse
import com.example.submission01.data.source.remote.response.ProductionCompaniesItem as  TvProductionCompaniesItem
import com.example.submission01.data.source.remote.response.GenresItem as  TvGenresItem

object Data {
    private val movies = ArrayList<DataClass>()
    private val tvShows = ArrayList<DataClass>()

    fun generateDataMovieDummyResponse(): List<DataMovieResponse> {
        val listMovie = ArrayList<DataMovieResponse>()

        listMovie.add(
            DataMovieResponse(
                id = 120,
                originalTitle = "The Lord of the Rings: The Fellowship of the Ring",
                overview = "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally\\'s career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
                posterPath = "/wrFpXMNBRj2PBiN4Z5kix51XaIZ.jpg",
                productionCompanies = listOf(MovieProductionCompaniesItem(name = "New Line Cinema")),
                genres = listOf(
                    MovieGenresItem(id = 1, name = "Adventure"),
                    MovieGenresItem(id = 2, name = "Fantasy"),
                    MovieGenresItem(id = 3, name = "Action")
                ),
                releaseDate = "2001-12-18",
                voteAverage = 8.4,
                voteCount = 19047,
                runtime = 179
            )
        )

        listMovie.add(
            DataMovieResponse(
                id = 121,
                originalTitle = "The Lord of the Rings: The Two Towers",
                overview = "Frodo and Sam are trekking to Mordor to destroy the One Ring of Power while Gimli, Legolas and Aragorn search for the orc-captured Merry and Pippin. All along, nefarious wizard Saruman awaits the Fellowship members at the Orthanc Tower in Isengard.",
                posterPath = "/5VTN0pR8gcqV3EPUHHfMGnJYN9L.jpg",
                productionCompanies = listOf(MovieProductionCompaniesItem(name = "New Line Cinema")),
                genres = listOf(
                    MovieGenresItem(id = 1, name = "Adventure"),
                    MovieGenresItem(id = 2, name = "Fantasy"),
                    MovieGenresItem(id = 3, name = "Action")
                ),
                releaseDate = "2002-12-18",
                voteAverage = 8.3,
                voteCount = 16448,
                runtime = 179
            )
        )

        return listMovie
    }

    fun generateDataSeriesDummyResponse(): List<DataTvResponse> {
        val listSeries = ArrayList<DataTvResponse>()
        tvShows.add(DataClass(121, "https://image.tmdb.org/t/p/w500/qgtHj2YyIMwGCCq2QQfWUvkqAUx.jpg","Doctor Who","BBC","1963-11-23","7.5 (2)","26 Seasons","Action & Adventure, Drama, Sci-Fi & Fantasy,","The adventures of a Time Lord—a time-travelling humanoid alien known as the Doctor—who explores the universe in his TARDIS, a sentient time-travelling space ship. Its exterior appears as a blue British police box, which was a common sight in Britain in 1963 when the series first aired. Along with a succession of companions, the Doctor faces a variety of foes while working to save civilisations, help ordinary people, and right wrongs."))
        tvShows.add(DataClass(122, "https://image.tmdb.org/t/p/w500/pWVyDYY83MpqdTBWrTtPDUrzflW.jpg","Adam Adamant Lives!","-","1966-06-23","5.0 (1)","2 Season","Drama, Comedy, Action & Adventure,","Adam Adamant Lives! is a British television series which ran from 1966 to 1967 on the BBC, starring Gerald Harper in the title role. Proposing that an adventurer born in 1867 had been revived from hibernation in 1966, the show was a comedy adventure that took a satirical look at life in the 1960s through the eyes of an Edwardian."))


        listSeries.add(
            DataTvResponse(
                id = 121,
                originalName = "Doctor Who",
                overview = "The adventures of a Time Lord—a time-travelling humanoid alien known as the Doctor—who explores the universe in his TARDIS, a sentient time-travelling space ship. Its exterior appears as a blue British police box, which was a common sight in Britain in 1963 when the series first aired. Along with a succession of companions, the Doctor faces a variety of foes while working to save civilisations, help ordinary people, and right wrongs.",
                posterPath = "/qgtHj2YyIMwGCCq2QQfWUvkqAUx.jpg",
                productionCompanies = listOf(TvProductionCompaniesItem(name = "BBC")),
                genres = listOf(
                    TvGenresItem(id = 1, name = "Action & Adventure"),
                    TvGenresItem(id = 2, name = "Drama"),
                    TvGenresItem(id = 3, name = "Sci-Fi & Fantasy")
                ),
                firstAirDate = "1963-11-23",
                voteAverage = 7.5,
                voteCount = 2,
                numberOfSeasons = 26
            )
        )

        listSeries.add(
            DataTvResponse(
                id = 122,
                originalName = "Adam Adamant Lives!",
                overview = "Adam Adamant Lives! is a British television series which ran from 1966 to 1967 on the BBC, starring Gerald Harper in the title role. Proposing that an adventurer born in 1867 had been revived from hibernation in 1966, the show was a comedy adventure that took a satirical look at life in the 1960s through the eyes of an Edwardian.",
                posterPath = "/pWVyDYY83MpqdTBWrTtPDUrzflW.jpg",
                productionCompanies = null,
                genres = listOf(
                    TvGenresItem(id = 1, name = "Drama"),
                    TvGenresItem(id = 2, name = "Comedy"),
                    TvGenresItem(id = 3, name = "Action & Adventure")
                ),
                firstAirDate = "1966-06-23",
                voteAverage = 5.0,
                voteCount = 1,
                numberOfSeasons = 2
            )
        )
        return listSeries
    }

    fun getTvShows(): ArrayList<DataClass> {
        tvShows.clear()
        tvShows.add(DataClass(121, "https://image.tmdb.org/t/p/w500/qgtHj2YyIMwGCCq2QQfWUvkqAUx.jpg","Doctor Who","BBC","1963-11-23","7.5 (2)","26 Seasons","Action & Adventure, Drama, Sci-Fi & Fantasy,","The adventures of a Time Lord—a time-travelling humanoid alien known as the Doctor—who explores the universe in his TARDIS, a sentient time-travelling space ship. Its exterior appears as a blue British police box, which was a common sight in Britain in 1963 when the series first aired. Along with a succession of companions, the Doctor faces a variety of foes while working to save civilisations, help ordinary people, and right wrongs."))
        tvShows.add(DataClass(122, "https://image.tmdb.org/t/p/w500/pWVyDYY83MpqdTBWrTtPDUrzflW.jpg","Adam Adamant Lives!","-","1966-06-23","5.0 (1)","2 Season","Drama, Comedy, Action & Adventure,","Adam Adamant Lives! is a British television series which ran from 1966 to 1967 on the BBC, starring Gerald Harper in the title role. Proposing that an adventurer born in 1867 had been revived from hibernation in 1966, the show was a comedy adventure that took a satirical look at life in the 1960s through the eyes of an Edwardian."))
        tvShows.add(DataClass(123, "https://image.tmdb.org/t/p/w500/dfoTgMdVKF08Sqp4AeLlMcLaVV7.jpg","Starting Over","-","2003-09-09","0.0 (0)","2 Seasons","-","Starting Over is an American reality TV show that follows the lives of women who are experiencing difficulty in their lives and want to make changes, with the help of life coaches. It was the first reality TV show to be nominated for a Daytime Emmy Award. Six women at a time work to overcome obstacles and meet personal goals. When it is determined that a woman has met all her goals, she \"graduates\" from the house and is replaced by a new roommate. On the other hand, if it's determined that she's not met her goals, she could be put on probation, or even asked to leave."))
        tvShows.add(DataClass(124, "https://image.tmdb.org/t/p/w500/7ikCv32zpkZ6n048iG0dpLLF2GG.jpg","Nationwide","-","1969-09-09","1.0 (1)","4 Seasons","-","Nationwide was a BBC News and current affairs television programme which ran from 9 September 1969 to 5 August 1983. It was broadcast on BBC One each weekday following the early evening news. It followed a magazine format, combining political analysis and discussion with consumer affairs, light entertainment and sports reporting. It began on 9 September 1969, running between Tuesdays and Thursdays at 6.00pm, before being extended to five days a week in 1972. From 1976 until 1981 the start time was 5:55pm. The final edition was broadcast on 5 August 1983, and the following October it was replaced by Sixty Minutes. The long-running Watchdog programme began as a Nationwide feature.\n" + "    \n" + "    The light entertainment was quite similar in tone to That's Life!. Eccentric stories featured skateboarding ducks and men who claimed that they could walk on egg shells.. Richard Stilgoe performed topical songs."))
        tvShows.add(DataClass(125, "https://image.tmdb.org/t/p/w500/ehAKEZ6gnxvrqguB4DuValXqb83.jpg","To the Manor Born","-","1979-09-30","7.3 (16)","3 Seasons","Comedy,","Sitcom about the love-hate relationship between upper-class Audrey fforbes Hamilton and Richard DeVere, the nouveau rich businessman who buys her manor house when she can longer afford to keep it"))
        tvShows.add(DataClass(126, "https://image.tmdb.org/t/p/w500/vdyW3bSsGdBe616Q3qNSoo8uOkG.jpg","Unsolved Mysteries","Cosgrove/Meurer Productions","1987-01-20","7.8 (41)","14 Seasons","Mystery, Drama,","Combines four to five segments of dramatic re-enactments, interviews and updates of real human and paranormal mysteries. An audience interactive call-to-action request allowed viewers to call in with tips to help solve the cases."))
        tvShows.add(DataClass(127, "https://i.ibb.co/jz5JjFM/gunslingergirl.jpg","Gunslinger Girl","Yuuka Nanri, Kanako Mitsuhashi, Eri Sendai, Ami Koshimizu, Hidenobu Kiuchi, Mitsuru Miyamoto, Masashi Ebara, Norihiro Inoue, Rie Nakagawa, Masami Iwasaki, Laura Bailey, Luci Christian, Caitlin Glass, Alese Johnson, Monica Rial, Jerry Jewell","2008","TV-14","2 Seasons","Anime Series, Crime TV Shows","On the surface, the Social Welfare Agency appears to help orphaned schoolgirls, but it's actually turning them into lethal agents."))
        tvShows.add(DataClass(128, "https://i.ibb.co/4KMQjdR/haikyuu.webp","Haikyu!!","Ayumu Murase, Kaito Ishikawa, Satoshi Hino, Miyu Irino, Yu Hayashi, Koki Uchiyama, Soma Saito, Nobuhiko Okamoto, Yoshimasa Hosoya, Toshiki Masuda, Kaori Nazuka, Hiroshi Kamiya, Kazunari Tanaka, Daisuke Namikawa, Hiroyuki Yoshino, Sumire Morohoshi, Yuichi Nakamura","2015","TV-14","2 Seasons","Anime Series, International TV Shows, Teen TV Shows","Inspired by a championship match he sees on TV, junior high schooler Hinata joins a volleyball club and begins training, despite his short height."))
        tvShows.add(DataClass(129, "https://i.ibb.co/Tv9cwfW/happish.jpg","Happyish","Steve Coogan, Kathryn Hahn, Sawyer Shipman, Bradley Whitford","2015","TV-MA","1 Season","TV Comedies, TV Dramas","A middle-aged adman is shaken up by his new boss, a 25-year-old who advises him to 'rebrand' himself. Is it possible, or is this as good as he'll get?"))
        tvShows.add(DataClass(130, "https://i.ibb.co/9WN3Vfz/infamilywetrust.jpg","In Family We Trust","Songsit Roongnophakunsri, Saksit Tangtong, Kathaleeya McIntosh, Sopitnapa Choompanee, Apasiri Nitibhon, Phollawat Manuprasert, Nappon Gomarachun, Patravadi Mejudhon, Kejmanee Wattanasin, Supoj Chancharoen, Pimmara Charoenpakdee, Thanapob Leeratanakajorn, Kritsanapoom Pibulsonggram","2018","TV-MA","1 Season","Crime TV Shows, International TV Shows, TV Dramas","When the head of the lucrative family business passes away, his will leaves the clan at extreme odds."))
        return tvShows
    }

    fun getMovies(): ArrayList<DataClass> {
        movies.clear()
        movies.add(DataClass(120, "https://image.tmdb.org/t/p/w500/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg","The Lord of the Rings: The Fellowship of the Ring","New Line Cinema","2001-12-18","8.4 (19047)","179 minutes","Adventure, Fantasy, Action,","Young hobbit Frodo Baggins, after inheriting a mysterious ring from his uncle Bilbo, must leave his home in order to keep it from falling into the hands of its evil creator. Along the way, a fellowship is formed to protect the ringbearer and make sure that the ring arrives at its final destination: Mt. Doom, the only place where it can be destroyed."))
        movies.add(DataClass(121, "https://image.tmdb.org/t/p/w500/5VTN0pR8gcqV3EPUHHfMGnJYN9L.jpg","The Lord of the Rings: The Two Towers","New Line Cinema","2002-12-18","8.3 (16448)","179 minutes","Adventure, Fantasy, Action,","Frodo and Sam are trekking to Mordor to destroy the One Ring of Power while Gimli, Legolas and Aragorn search for the orc-captured Merry and Pippin. All along, nefarious wizard Saruman awaits the Fellowship members at the Orthanc Tower in Isengard."))
        movies.add(DataClass(122, "https://image.tmdb.org/t/p/w500/rCzpDGLbOoPwLjy3OAm5NUPOTrC.jpg","The Lord of the Rings: The Return of the King","New Line Cinema","2003-12-01","8.5 (17571)","201 minutes","Adventure, Fantasy, Action,","Aragorn is revealed as the heir to the ancient kings as he, Gandalf and the other members of the broken fellowship struggle to save Gondor from Sauron's forces. Meanwhile, Frodo and Sam take the ring closer to the heart of Mordor, the dark lord's realm."))
        movies.add(DataClass(123, "https://image.tmdb.org/t/p/w500/4O3s0IGZJirPBecqEnP9qsjlTQw.jpg","The Lord of the Rings","Fantasy Films","1978-11-15","6.5 (551)","132 minutes","Adventure, Animation, Fantasy,","The Fellowship of the Ring embark on a journey to destroy the One Ring and end Sauron's reign over Middle-earth."))
        movies.add(DataClass(124, "https://image.tmdb.org/t/p/w500/3pdF1guvIxNgmhDkmXJHCCk0PkC.jpg","Bez końca","P.P. Film Polski","1985-06-17","7.6 (43)","108 minutes","Drama,","1982, Poland. A translator loses her husband and becomes a victim of her own sorrow. She looks to sex, to her son, to law, and to hypnotism when she has nothing else in this time of martial law when Solidarity was banned."))
        movies.add(DataClass(125, "https://image.tmdb.org/t/p/w500/c4xG9QoCx2zXc8iP3jQVLYDHiMi.jpg","Dworzec","WFD","1980-01-01","5.9 (15)","13 minutes","Documentary,","Kieslowski’s later film Dworzec (Station, 1980) portrays the atmosphere at Central Station in Warsaw after the rush hour."))
        movies.add(DataClass(126, "https://image.tmdb.org/t/p/w500/rJwiDSoKI3ryiLTZZ6BPUl2CrwG.jpg","Krótki dzień pracy","Zespól Filmowy \"Tor\"","1981-06-27","6.8 (4)","73 minutes","Drama, TV Movie,","Kieślowski’s study of a political strike, controversially told from the viewpoint of a Communist functionary trying to keep order."))
        movies.add(DataClass(127, "https://image.tmdb.org/t/p/w500/gx84VmJllqvXF3JEqcEdsxBEcbF.jpg","Przypadek","P.P. Film Polski","1987-01-10","7.6 (108)","123 minutes","Drama,","Witek runs after a train. Three variations follow on how such a seemingly banal incident could influence the rest of Witek's life."))
        movies.add(DataClass(128, "https://image.tmdb.org/t/p/w500/pdtzEreKvKAlqa2YEBaGwiA45V8.jpg","もののけ姫","Studio Ghibli","1997-07-12","8.4 (5564)","134 minutes","Adventure, Fantasy, Animation,","Ashitaka, a prince of the disappearing Emishi people, is cursed by a demonized boar god and must journey to the west to find a cure. Along the way, he encounters San, a young human woman fighting to protect the forest, and Lady Eboshi, who is trying to destroy it. Ashitaka must find a way to bring balance to this conflict."))
        movies.add(DataClass(129, "https://image.tmdb.org/t/p/w500/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg","千と千尋の神隠し","Studio Ghibli","2001-07-20","8.5 (11302)","125 minutes","Animation, Family, Fantasy,","A young girl, Chihiro, becomes trapped in a strange new world of spirits. When her parents undergo a mysterious transformation, she must call upon the courage she never knew she had to free her family."))

        return movies
    }

}