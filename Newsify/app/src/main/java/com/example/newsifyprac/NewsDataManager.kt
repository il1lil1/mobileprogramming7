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

    val scope = CoroutineScope(Dispatchers.IO)

    val NewsReadCount = 2

    fun initNewsData() {
        getNewsJtbc()
        getNewsMbc()
    }

    fun getNewsMbc() {
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

                    Log.i("NEWS_MBC", reporter)

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