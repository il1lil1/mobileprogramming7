package com.example.newsifyprac

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class NewsDataManager {
    var newsList = arrayListOf<NewsData>()

    val jtbcRSS = arrayListOf<String>(
        "https://fs.jtbc.co.kr/RSS/politics.xml", //정치
        "https://fs.jtbc.co.kr/RSS/economy.xml", //경제
        "https://fs.jtbc.co.kr/RSS/society.xml", //사회
        "https://fs.jtbc.co.kr/RSS/international.xml", //국제
        "https://fs.jtbc.co.kr/RSS/culture.xml", //문화
        "https://fs.jtbc.co.kr/RSS/entertainment.xml", //연예
        "https://fs.jtbc.co.kr/RSS/sports.xml" //스포츠
    )

    val jtbcCategory = hashMapOf(
        "https://fs.jtbc.co.kr/RSS/politics.xml" to "politics",
        "https://fs.jtbc.co.kr/RSS/economy.xml" to "economy",
        "https://fs.jtbc.co.kr/RSS/society.xml" to "society",
        "https://fs.jtbc.co.kr/RSS/international.xml" to "international",
        "https://fs.jtbc.co.kr/RSS/culture.xml" to "culture",
        "https://fs.jtbc.co.kr/RSS/entertainment.xml" to "entertainment",
        "https://fs.jtbc.co.kr/RSS/sports.xml" to "sports"
    )

    val sbsRSS = arrayListOf<String>(
        "https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=01&plink=RSSREADER", //정치
        "https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=02&plink=RSSREADER", // 경제
        "https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=03&plink=RSSREADER", // 사회
        "https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=07&plink=RSSREADER", // 생활/문화
        "https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=08&plink=RSSREADER", // 국제/글로벌
        "https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=14&plink=RSSREADER", // 연예/방송
        "https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=09&plink=RSSREADER" // 스포츠
    )

    val sbsCategory = hashMapOf(
        "https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=01&plink=RSSREADER" to "politics",
        "https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=02&plink=RSSREADER" to "economy",
        "https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=03&plink=RSSREADER" to "society",
        "https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=08&plink=RSSREADER" to "international",
        "https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=07&plink=RSSREADER" to "culture",
        "https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=14&plink=RSSREADER" to "entertainment",
        "https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=09&plink=RSSREADER" to "sports"
    )

    val chosunRss = arrayListOf<String>(
    	"https://www.chosun.com/arc/outboundfeeds/rss/category/politics/?outputType=xml", //정치
    	"https://www.chosun.com/arc/outboundfeeds/rss/category/economy/?outputType=xml", // 경제
    	"https://www.chosun.com/arc/outboundfeeds/rss/category/national/?outputType=xml", // 사회
    	"https://www.chosun.com/arc/outboundfeeds/rss/category/international/?outputType=xml", // 국제
        "https://www.chosun.com/arc/outboundfeeds/rss/category/culture-life/?outputType=xml", // 문화/라이프
        "https://www.chosun.com/arc/outboundfeeds/rss/category/sports/?outputType=xml", // 스포츠
        "https://www.chosun.com/arc/outboundfeeds/rss/category/entertainments/?outputType=xml", // 연예
    )

    val chosunCategory = hashMapOf(
        "https://www.chosun.com/arc/outboundfeeds/rss/category/politics/?outputType=xml"  to "politics",
        "https://www.chosun.com/arc/outboundfeeds/rss/category/economy/?outputType=xml" to "economy",
        "https://www.chosun.com/arc/outboundfeeds/rss/category/national/?outputType=xml" to "society",
        "https://www.chosun.com/arc/outboundfeeds/rss/category/international/?outputType=xml" to "international",
        "https://www.chosun.com/arc/outboundfeeds/rss/category/culture-life/?outputType=xml" to "culture",
        "https://www.chosun.com/arc/outboundfeeds/rss/category/sports/?outputType=xml" to "sports",
        "https://www.chosun.com/arc/outboundfeeds/rss/category/entertainments/?outputType=xml" to "entertainment"
    )

    val scope = CoroutineScope(Dispatchers.IO)

    val NewsReadCount = 2

    fun initNewsData() {
        getNewsJtbc()
        getNewsSbs()
        getNewsChosun()
    }

    fun DebugAllData() {
        for (data in newsList) {
            Log.i("DEBUG.LOG", data.title)
            Log.i("DEBUG.LOG", data.url)
            Log.i("DEBUG.LOG", data.content)
            Log.i("DEBUG.LOG", data.date)
            Log.i("DEBUG.LOG", data.category)
            Log.i("DEBUG.LOG", data.broadcaster)
            Log.i("DEBUG.LOG", data.reporter)
        }
    }

    fun getNewsSbs() {
        scope.launch {
            var i = 0
            for (rssurlSbs in sbsRSS) {
                val docRss = Jsoup.connect(rssurlSbs).parser(Parser.xmlParser()).get()
                val headline  = docRss.select("item")

                for (news in headline) {
                    val urlToselect = news.select("link").text()
                    var reporter = news.select("category")[2].text()
                    if (!Regex("""(.+) 기자$""").matches(reporter)) {
                        reporter = "SBS 편집부"
                    }

                    newsList.add(NewsData(news.select("title").text(), urlToselect,
                        reporter, news.select("pubDate").text(),
                        news.select("description").text(), sbsCategory[rssurlSbs].toString(), "SBS"))
                    i++
                    if (i > NewsReadCount) {
                        i = 0
                        break
                    }

                }

            }
            //DebugAllData()
        }
    }

    fun getNewsJtbc() {
        scope.launch {
            var i = 0
            for (rssurlJtbc in jtbcRSS) {
                val docRss = Jsoup.connect(rssurlJtbc).parser(Parser.xmlParser()).get()
                val headline  = docRss.select("item")

                for (news in headline) {
                    val urlToselect = news.select("link").text()
                    val docUrl = Jsoup.connect(urlToselect).get()
                    val reporter = docUrl.select("#Head1>[name=Author]")

                    newsList.add(NewsData(news.select("title").text(), urlToselect,
                        reporter.attr("content").toString() + " 기자", news.select("pubDate").text(),
                        news.select("description").text(), jtbcCategory[rssurlJtbc].toString(), "JTBC"))
                    i++
                    if (i > NewsReadCount) {
                        i = 0
                        break
                    }

                }
            }
            //DebugAllData()
        }
    }

    fun getNewsChosun() {
        scope.launch {
            var i = 0
            for (rssurlChosun in chosunRss) {
                val docRss = Jsoup.connect(rssurlChosun).parser(Parser.xmlParser()).get()
                val headline  = docRss.select("item")

                for (news in headline) {
                    val urlToselect = news.select("link").text()
                    val reporter = news.select("dc\\:creator").text()

                    newsList.add(NewsData(news.select("title").text(), urlToselect,
                        reporter, news.select("pubDate").text(),
                        news.select("content\\:encoded").text(), chosunCategory[rssurlChosun].toString(), "조선일보"))
                    i++
                    if (i > NewsReadCount) {
                        i = 0
                        break
                    }

                }
            }
            DebugAllData()
        }
    }

    fun getNews(): ArrayList<NewsData> {
        return newsList;
    }

    fun getNewsByCategory(category:String): ArrayList<NewsData> {
        var newsByCategoryList = arrayListOf<NewsData>()

        for (news in newsList) {
            if (category == news.category) {
                newsByCategoryList.add(news)
            }
        }
        return newsByCategoryList;
    }

    fun getNewsByReporter(reporter:String): ArrayList<NewsData> {
        var newsByReporterList = arrayListOf<NewsData>()

        for (news in newsList) {
            if (reporter == news.reporter) {
                newsByReporterList.add(news)
            }
        }
        return newsByReporterList;
    }

    fun getNewsByBroadcaster(broadcaster:String): ArrayList<NewsData> {
        var newsByBroadcasterList = arrayListOf<NewsData>()

        for (news in newsList) {
            if (broadcaster == news.broadcaster) {
                newsByBroadcasterList.add(news)
            }
        }
        return newsByBroadcasterList;
    }
}