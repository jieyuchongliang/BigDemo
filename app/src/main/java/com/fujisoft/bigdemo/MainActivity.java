package com.fujisoft.bigdemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fujisoft.bigdemo.bean.Comment;
import com.fujisoft.bigdemo.bean.News;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button insert, inquiry, update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        SQLiteDatabase db = Connector.getDatabase();
    }

    private void initView() {
        insert = (Button) findViewById(R.id.insert);
        inquiry = (Button) findViewById(R.id.inquiry);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        insert.setOnClickListener(this);
        inquiry.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert:
                insert();
                break;
            case R.id.inquiry:
                inquiry();
                break;
            case R.id.update:
                update();
                break;
            case R.id.delete:
                delete();
                break;
            default:
                break;
        }
    }

    private void delete() {
        //想删除news表中id为2的记录，就可以这样写：
        DataSupport.delete(News.class, 2);

        //把news表中标题为“今日iPhone6发布”且评论数等于0的所有新闻都删除掉，就可以这样写：
        DataSupport.deleteAll(News.class, "title = ? and commentcount = ?", "今日iPhone6发布", "0");

        //想把news表中所有的数据全部删除掉，就可以这样写：
        DataSupport.deleteAll(News.class);
    }

    private void update() {
        //修改指定id的title数据
//        News updateNews = new News();
//        updateNews.setTitle("今日iPhone6发布");
//        updateNews.update(2);

        //把news表中标题为“今日iPhone6发布”且评论数量大于0的所有新闻的标题改成“今日iPhone6 Plus发布”
//        News updateNews = new News();
//        updateNews.setTitle("今日iPhone6发布");
//        updateNews.updateAll("title = ? and commentcount > ?", "今日iPhone6发布", "0");

        //修改全部信息
        News updateNews = new News();
        updateNews.setToDefault("commentCount");
        updateNews.updateAll();
    }

    private void inquiry() {
        //查询一条数据
        News news = DataSupport.find(News.class, 1);

        //获取news表中的第一条数据，只需要这样写：
        News firstNews = DataSupport.findFirst(News.class);

        //想要获取News表中的最后一条数据
        News lastNews = DataSupport.findLast(News.class);

        //想把news表中id为1、3、5、7的数据都查出来
        List<News> newsList = DataSupport.findAll(News.class, 1, 3, 5, 7);

        //要查询的多个id已经封装到一个数组里了。那么没关系，findAll()方法也是接收数组参数的，所以说同样的功能你也可以这样写：
        long[] ids = new long[] { 1, 3, 5, 7 };
        List<News> newsLists = DataSupport.findAll(News.class, ids);

        //查询所有数据
        List<News> allNews = DataSupport.findAll(News.class);

        //想查询news表中所有评论数大于零的新闻，就可以这样写：
        List<News> newList = DataSupport.where("commentcount > ?", "0").find(News.class);

        //上面做法会将news表中所有的列都查询出来，也许你并不需要那么多的数据，而是只要title和content这两列数据。
        // 那么也很简单，我们只要再增加一个连缀就行了，如下所示：
        List<News> newsListSelect = DataSupport.select("title", "content")
                .where("commentcount > ?", "0").find(News.class);

        //希望将查询出的新闻按照发布的时间倒序排列，即最新发布的新闻放在最前面，那就可以这样写：
        //order()方法中接收一个字符串参数，用于指定查询出的结果按照哪一列进行排序，asc表示正序排序，
        // desc表示倒序排序，因此order()方法对应了一条SQL语句中的order by部分。
        List<News> newsList1 = DataSupport.select("title", "content")
                .where("commentcount > ?", "0")
                .order("publishdate desc").find(News.class);

        //也许你并不希望将所有条件匹配的结果一次性全部查询出来，因为这样数据量可能会有点太大了，
        // 而是希望只查询出前10条数据，那么使用连缀同样可以轻松解决这个问题，代码如下所示：
        List<News> newsList2 = DataSupport.select("title", "content")
                .where("commentcount > ?", "0")
                .order("publishdate desc").limit(10).find(News.class);

        //刚才我们查询到的是所有匹配条件的前10条新闻，那么现在我想对新闻进行分页展示，翻到第二页时，
        // 展示第11到第20条新闻，这又该怎么实现呢？没关系，在LitePal的帮助下，这些功能都是十分简单的，
        // 只需要再连缀一个偏移量就可以了，如下所示：
        List<News> newsList3 = DataSupport.select("title", "content")
                .where("commentcount > ?", "0")
                .order("publishdate desc").limit(10).offset(10)
                .find(News.class);
    }

    private void insert() {
//        News news = new News();
//        news.setTitle("这是一条新闻标题");
//        news.setContent("这是一条新闻内容");
//        news.setPublishDate(new Date());
//        Log.d("TAG", "news id is " + news.getId());
//        boolean save = news.save();
//        Log.d("TAG", "news id is " + news.getId());
//
//        if (save) {
//            Toast.makeText(this, "存储成功", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "存储失败", Toast.LENGTH_SHORT).show();
//        }
        Comment comment1 = new Comment();
        comment1.setContent("好评！");
        comment1.setPublishDate(new Date());
        comment1.save();
        Comment comment2 = new Comment();
        comment2.setContent("赞一个");
        comment2.setPublishDate(new Date());
        comment2.save();
        News news = new News();
        news.getCommentList().add(comment1);
        news.getCommentList().add(comment2);
        news.setTitle("第二条新闻标题");
        news.setContent("第二条新闻内容");
        news.setPublishDate(new Date());
        news.setCommentCount(news.getCommentList().size());
        boolean save = news.save();
        if (save) {
            Toast.makeText(this, "存储成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "存储失败", Toast.LENGTH_SHORT).show();
        }
    }
}
