# 获取文本 a/text()
# @符号 选择a标签的href属性 a/@href   选择id为detail-list的ul标签，//ul[@id="detail-list"]
# //div[contains(@class,'i')]    class包含i的div

from lxml import etree

text = """
<div class="movie_top" id="ranking">
    <ul class="content" id="listCont2">
        <li class="clearfix xh-highlight">
            <div class="no">1</div>
            <div class="name">
                <a onclick="moreurl(this, {from:'mv_week'})" href="https://movie.douban.com/subject/27615441/" class="">
                    网络谜踪
                </a>
            </div>
            <span class="">
                    <div class="up">10</div>
            </span>
        </li>
        <li class="clearfix xh-highlight">
            <div class="no">2</div>
            <div class="name">
                <a onclick="moreurl(this, {from:'mv_week'})" href="https://movie.douban.com/subject/27608785/" class="">
                    沉默的教室
                </a>
            </div>
            <span class="">
                    <div class="stay">0</div>
            </span>
        </li>
        <li class="clearfix xh-highlight">
            <div class="no">3</div>
            <div class="name">
                <a onclick="moreurl(this, {from:'mv_week'})" href="https://movie.douban.com/subject/26784898/" class="">
                    花牌情缘：结
                </a>
            </div>
            <span class="">
                    <div class="up">8</div>
            </span>
        </li>
        <li class="clearfix xh-highlight">
            <div class="no">4</div>
            <div class="name">
                <a onclick="moreurl(this, {from:'mv_week'})" href="https://movie.douban.com/subject/27165968/" class="">
                    少年泰坦出击电影版
                </a>
            </div>
            <span class="">
                    <div class="up">7</div>
            </span>
        </li>
        <li class="clearfix xh-highlight">
            <div class="no">5</div>
            <div class="name">
                <a onclick="moreurl(this, {from:'mv_week'})" href="https://movie.douban.com/subject/26979199/" class="">
                    喜欢，轻吻，快跑
                </a>
            </div>
            <span class="">
                    <div class="down">2</div>
            </span>
        </li>
        <li class="clearfix xh-highlight">
            <div class="no">6</div>
            <div class="name">
                <a onclick="moreurl(this, {from:'mv_week'})" href="https://movie.douban.com/subject/26985996/" class="">
                    私人生活
                </a>
            </div>
            <span class="">
                    <div class="up">5</div>
            </span>
        </li>
        <li class="clearfix xh-highlight">
            <div class="no">7</div>
            <div class="name">
                <a onclick="moreurl(this, {from:'mv_week'})" href="https://movie.douban.com/subject/27129755/" class="">
                    人言可畏
                </a>
            </div>
            <span class="">
                    <div class="up">4</div>
            </span>
        </li>
        <li class="clearfix xh-highlight">
            <div class="no">8</div>
            <div class="name">
                <a onclick="moreurl(this, {from:'mv_week'})" href="https://movie.douban.com/subject/26944582/" class="">
                    雪怪大冒险
                </a>
            </div>
            <span class="">
                    <div class="up">3</div>
            </span>
        </li>
        <li class="clearfix xh-highlight">
            <div class="no">9</div>
            <div class="name">
                <a onclick="moreurl(this, {from:'mv_week'})" href="https://movie.douban.com/subject/26682880/" class="">
                    过境
                </a>
            </div>
            <span class="">
                    <div class="up">2</div>
            </span>
        </li>
        <li class="clearfix xh-highlight">
            <div class="no">10</div>
            <div class="name">
                <a onclick="moreurl(this, {from:'mv_week'})" href="https://movie.douban.com/subject/26794854/" class="">
                    帝企鹅日记2：召唤
                </a>
            </div>
            <span class="">
                    <div class="up">1</div>
            </span>
        </li>
    </ul>

    </div>
"""

html = etree.HTML(text)
print(etree.tostring(html).decode())

print("-"*20)
ret = html.xpath("//li[@class='clearfix xh-highlight']//a/@href")
print(ret)