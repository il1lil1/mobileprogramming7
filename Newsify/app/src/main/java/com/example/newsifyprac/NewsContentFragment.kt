package com.example.newsifyprac

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class NewsContentFragment : Fragment() {
    private lateinit var myViewModel: MyViewModel

    var contentData:NewsData? = null
    val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = requireActivity() as MainActivity
        myViewModel = mainActivity.myViewModel


        var contentTitle: TextView = view.findViewById(R.id.contentTitle)
        var contentDate: TextView = view.findViewById(R.id.contentDate)
        var contentReporter: TextView = view.findViewById(R.id.contentReporter)
        var contentContent: TextView = view.findViewById(R.id.contentContent)

        contentTitle.text = contentData?.title
        contentDate.text = contentData?.date
        contentReporter.text = contentData?.reporter

        contentContent.textSize = myViewModel.TextSize.toFloat()

        scope.launch {
            when {
                contentData?.broadcaster == "조선일보" -> {
                    getNewsChosun(contentData?.url, contentContent)
                }
                contentData?.broadcaster == "SBS" -> {
                    getNewsSbs(contentData?.url, contentContent)
                }
                contentData?.broadcaster == "JTBC" -> {
                    getNewsJtbc(contentData?.url, contentContent)
                }
            }
        }
    }

    suspend fun getNewsChosun(url: String?, contentContent: TextView) {
        val str = withContext(Dispatchers.IO) {
            val docUrl = Jsoup.connect(url).get()
            var content = docUrl.select("script#fusion-metadata").toString()

            var indexNum = 0
            val endIndex = content.indexOf("\"author\"")

            content = content.subSequence(0, endIndex).toString()

            var printStr = ""

            while (indexNum != -1) {
                indexNum = content.indexOf("\"content\":\"", indexNum) + 11
                val startIndex = indexNum
                if (indexNum > endIndex || indexNum < 20) {
                    break
                }

                while (indexNum != -1) {
                    if (content[indexNum] == '\"') {
                        if (startIndex > indexNum) {
                        }
                        printStr += content.subSequence(startIndex, indexNum)
                        break
                    }
                    indexNum++
                }

                printStr += "\n\n"
            }

            printStr
        }

        withContext(Dispatchers.Main) {
            contentContent.text = str
        }
    }


    suspend fun getNewsSbs(url: String?, contentContent: TextView) {
        val str = withContext(Dispatchers.IO) {
            val docUrl = Jsoup.connect(url).parser(Parser.htmlParser()).get()
            val content = docUrl.select(".text_area")

            content.text().replace(". ", ".\n\n")
        }

        withContext(Dispatchers.Main) {
            contentContent.text = str
        }
    }

    suspend fun getNewsJtbc(url: String?, contentContent: TextView) {
        val str = withContext(Dispatchers.IO) {
            val docUrl = Jsoup.connect(url).parser(Parser.htmlParser()).get()
            val content = docUrl.select(".article_content")[0]

            content.text().replace(". ", ".\n\n")
        }

        withContext(Dispatchers.Main) {
            contentContent.text = str
        }
    }
}
