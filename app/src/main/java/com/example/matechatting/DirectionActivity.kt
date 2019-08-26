package com.example.matechatting

import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matechatting.base.BaseActivity
import com.example.matechatting.databinding.ActivityDirectionBinding

import com.example.matechatting.utils.statusbar.StatusBarUtil

class DirectionActivity : BaseActivity<ActivityDirectionBinding>() {


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var gridView: GridView
    private lateinit var back: FrameLayout
    private lateinit var drawer_content: LinearLayout
    private lateinit var recycleView: RecyclerView
    private lateinit var lable: TextView
    private var mAdapter: GridViewAdapter? = null
    val manager: GridLayoutManager = GridLayoutManager(this, 3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
        StatusBarUtil.setStatusBarDarkTheme(this, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setStatusBarColor(this, this.getColor(R.color.bg_ffffff))
        }
        canSlideFinish(true)
        initBinding()
        initView()
        initDirection()
    }

    /**
     * 分离字符，隔开空格
     */
    private fun initString(info: String): List<String> {
        var list = info.trim().split(" ")
        list.forEach {
            if (it.isNullOrEmpty())
                list -= it
        }
        return list
    }

    /**
     * 初始化方向
     */
    private fun initDirection() {
        val dir_xiaoshou =
            "销售代表  电话销售  客户经理  营业员  置业顾问/房产经纪人  汽车销售顾问 医药代表  渠道/分销专员  团购业务员  招商专员  销售内勤/助理  网络/在线销售 驻店销售  销售总监  销售经理  渠道/分销  团购经理  大客户经理  区域销售管理  招商经理"
        val dir_kefu = "客服总监  客服经理  客服专员  网店客服  客户关系管理  售后/售后技术支持"
        val dir_shichang =
            "市场营销总监  市场营销经理  市场营销专员  商务专员  选址拓展/新店开发 活动策划/执行企划  促销员/督导  网络营销/推广  海外市场  品牌经理/主管品牌专员  市场分析/调研  政府关系  商务经理"
        val dir_gongguan = "公关/媒介经理  公关/媒介专员  品牌经营  广告协调  会务安排  媒介投放"
        val dir_renliziyuan = "人力资源专员  招聘专员  培训专员  薪酬福利专员  绩效专员  员工关系专员  人事助理  人力资源经理/主管  人力资源总监"
        val dir_xingzheng =
            "行政专员  文员  前台接待  高管助理/秘书  系统管理员/网管  保安  保洁  商务司机客运司机  货运司机  党工团干事  资料录入/打字员  资料管理员  办公室主任  后勤 行政经理/主管  行政总监"
        val dir_caiwu = "财务专员  会计  出纳  投融资  审计专员  税务会计  财务统计员  成本会计 结算专员  风控专员  资金/资产会计  财务经理/主管  财务总监"
        val dir_lvshi = "律师/法律顾问  法务经理/主管  法务专员  知识产权/专利顾问  合规管理"
        val dir_gaojiguanli = "CEO  副总  CTO  COO  CFO  办事处/分公司负责人  总工程师  厂长/副厂长  合伙人 总经理助理  投资者关系  其他(高级管理)"
        val dir_shengchan =
            " 厂长/副厂长  总工程师/副总工程师  车间主任  项目经理  技术工程师  营运经理/主管生产计划/调度  物料管理/库管  生产主管/领班/组长  工艺设计  化验/检验  生产文员设备管理  其他（生产/营运）"
        val dir_gongren =
            "普工  钣金工  机修工  冲压工  装配工  焊工  钳工  车工  磨工  铣工  切割技工  模具工  叉车工/铲车  空调工  电梯工  锅炉工  水电工  木工  油漆工  注塑工  铸造/锻造  万能工  组装工  包装工  旋压工  仪表工  电镀工  喷塑工  折弯工 刨工  钻工  镗工  铆工  抛光工  炼胶工  硫化工  吹膜工  熔炼工  浇注工  造型工 清理工  涂装工  其他（普工/技工）"
        val dir_zhikong = "质量管理/测试经理  质量检验员/测试员  可靠度工程师  认证体系工程师/审核员 供应商管理（SQE）  安全消防  环保  其他（质控/安防）"
        val dir_hulianwang =
            "PHP  Java  .Net  C/C++  C#  python  架构师  其他（后端开发）  数据挖掘 搜索算法  精准推荐  全栈工程师  Delphi  VB  Perl  Ruby  Node.js  Go  ASP  Shell区块链  物联网工程师  大数据工程师  云计算 IOS  android  html5(移动)  其他（移动开发）网页前端  html5(前端)  JavaScript  Flash  COCOS2D-X/U3D  其他（前端开发） 深度学习  机器学习  图像处理  图像识别  语音识别  机器视觉  算法工程师 自然语言处理运维工程师  网络安全  运维经理 mysql  SQL   Server  Oracle  Hadoop  DBA  DB2  MongoDB  ET  LHive  数据仓库项目经理/助理  技术经理  CTO软件实施  系统集成  系统分析员软件测试  移动端测试  游戏测试  测试工程师  测试主管/经理  测试总监 网页产品经理  移动产品经理  电商产品经理  软件产品经理  游戏策划  产品经理 产品专员/助理  数据产品经理  产品部主管/经理  产品总监  用户研究员  数据分析师 UI设计  平面设计  网页设计/美工  游戏设计/原画设计  交互设计  设计师 设计主管/经理  设计总监 网店运营  SEO/ASO/SEM  网络推广  文案策划  编辑/内容运营  新媒体运营 市场策划  运营/市场专员  运营/市场经理  运营/市场总监  用户运营  数据运营 活动运营  商家运营  品类运营  游戏运营  游戏推广  电竞选手/游戏代练  游戏体验师"
        val dir_tongxin =
            "移动通信工程师  有线传输工程师  网络工程师  通信项目管理  安装技术员  通信技术工程师  通信设备维修  无线通信工程师  电信交换工程师  数据通信工程师 电信网络工程师  通信电源工程师  硬件射频工程师  硬件基带工程师  驱动工程师 物联网工程师  其他（通信/网络设备） 电源开发工程师  工艺工程师  汽车电子工程师  自动化工程师  嵌入式开发  仪器/仪表/计量  测试工程师  SMT工程师  电子工程师家电/数码产品研发 光源和照明工程师  音响工程师  电器维修  电器工程师"
        val dir_qiche =
            "电器设计师  底盘设计师  车身/造型设计师  动力系统工程师  内外饰工程师  机电工程师  涂装工程师  总布置工程师  车辆试验/测试  质量工程师  其他（汽车研发/设计）  汽车设计工程师  汽车电子工程师   发动机/总装工程师  汽车项目管理  汽车质量管理  汽车安全性能工程师  汽车装配工艺工程师 汽车销售顾问  精品销售  零配件销售  销售内勤  销售经理  市场营销专员  市场营销经理  二手车评估师  销售库管汽车美容  洗车工  售后服务/客服  售后经理/主管  保险理赔  机电维修  维修钣金工维修漆工  其他（服务/售后/维修） 模具设计师  机械工程师  机电工程师  CNC工程师  夹具设计师  结构工程师  绘图员设备管理  设备维修  工艺工程师  工业工程师  材料工程师  技术研发  技术研发经理/主管  注塑工程师/技师  焊接工程师/技师  冲压工程师/技师 锅炉工程师/技师  电机设计师  工业机器人工程师  其他（机械/设备）"
        val dir_fangdichan =
            "总建筑师/设计总监  建筑设计师  绘图/效果图制作  规划设计师  方案设计师  结构设计师  暖通设计师  给排水设计师  电气设计师  幕墙设计师  施工图设计师  园林/景观设计  建筑信息模型  其他（建筑设计）  总工程师/工程总监  工程经理  建筑工程师/建造师  招投标  配套工程师  开发报建 预结算/造价土建工程师  安装工程师  路桥/市政工程师  岩土工程师  智能楼宇 测绘/测量  施工员  施工管理/工长  资料员  采购/材料  安全员  质检员  监理工程师 建筑机电工程师  给排水/暖通工程师  幕墙工程师  建筑工程验收  建筑安装施工员  砌筑工  瓦工  混凝土工  浇注工  钢筋工  弱电工程师  园林工程师  其他（建筑工程） 营销/策划总监  营销/策划经理  营销/策划专员  置业顾问/房产经纪人  销售经理  招商专员  招商经理  权证员  其他（营销/策划/销售） 设计总监  设计经理/主管  设计师/高级设计师  家装设计师  工装设计师  软装设计师  绘图员  家装顾问  工程/项目总监  工程/项目经理  工程监理  施工员  水电工  木工泥工  资料员  材料专员/经理  设计师助理  验房师  其他（装饰装修）物业经理/主管  物管员  招商/租售  综合维修工  水电维修工  保安经理  保安  保洁绿化工  品质经理  其他（物业）"
        val dir_jinrong =
            "投融资专员  投融资经理/主管  证券分析师  金融产品经纪人  投资银行业务  金融操盘手  金融研究员  投资/基金项目经理  投资顾问  风控专员  金融产品经理  拍卖/担保/典当  证券事务代表  其他（金融/证券/投资）"
        val dir_yinhang = "行长/副行长  业务部门经理/主管  客户经理  综合业务专员  风险控制  信审核查  大堂经理  资产评估/分析  信贷管理  银行柜员  信用卡销售  其他（银行）"
        val dir_baoxian = "保险经纪人/客户经理  理财顾问  保险业务经理  保险理赔  车险专员  客户服务/续期管理  保险培训师  保险内勤  保险产品开发  其他（保险）"
        val dir_guanggao = "广告客户总监  广告销售经理  广告客户专员  广告创意总监  广告创意/设计  美术指导 文案策划  广告执行/制作/安装  会务/会展  其他（广告）"
        val dir_sheji =
            "平面设计师  动画/3D设计  网页设计  UI设计  美工  装修设计  家具设计  原画师 家居用品设计  服装设计  建筑设计  工艺品/珠宝设计  工业设计  店面/展览设计  设计经理/主管  设计总监  漫画师  其他（设计）"
        val dir_chuanmei = "编导/导演  主持人/司仪  演员/模特  摄像/摄影  化妆师/造型师  影视策划  后期制作/剪辑师  经纪人  灯光师  主播  其他（影视/媒体）"
        val dir_xiezuo = "编辑  美术编辑  记者  校对/录入  排版设计  制版/印刷操作  其他（写作/出版/印刷）"
        val dir_canyin =
            "餐饮/娱乐管理  前厅经理/领班  厨师长/行政总厨  厨师/炉子  打荷/杂工  墩子/切配 送餐员  面点师  咖啡师  服务员  传菜员  迎宾/咨客  调酒师/吧台员  茶艺师  学徒洗碗工  收银员  挑面师傅  打锅师傅  保洁  店长  库管  饮品店员  西点师  其他（餐饮/娱乐）"
        val dir_jiudian =
            "酒店管理  大堂经理/领班  酒店销售  前台接待  预定员  服务员  行李员  保安  保洁 旅游销售  旅游计调  导游  宴会管理  楼面经理  客房服务  签证专员  票务  机场代表  船员/海员  旅游翻译  旅游线路设计  话务员  工程部主管  洗衣房管理  公区管理  其他（酒店/旅游）"
        val dir_meirong =
            "美容师/化妆师  美容顾问  美甲师  发型师  宠物美容  按摩/足疗  健身顾问  健身教练  体育运动教练  瑜伽老师  舞蹈老师  彩妆培训师  发型助理/学徒  游泳教练 救生员  高尔夫教练  其他（美容/美发/按摩）"
        val dir_baoan = "保安经理  保安  搬运工  保洁  保姆/护工  月嫂  钟点工  育婴师/保育员  其他（保安/家政）"
        val dir_baihuo = "店长  卖场经理  品类经理  营业员  导购员  防损员  收银员  收货员/理货员/陈列员 食品加工/处理  招商人员  督导  驻店销售  其他（百货/零售）"
        val dir_hunqin = "主持人/司仪  演员/模特  摄像/摄影  化妆师/造型师  影视策划  后期制作/剪辑师灯光师  DJ  驻唱/歌手  舞蹈演员"
        val dir_muying = "婴儿游泳  婴儿按摩/抚触  月嫂  育婴师/育儿嫂  产后恢复"
        val dir_yiliao =
            "内科医生  外科医生  麻醉医生  妇产科医生  五官科医生  儿科医生  中医医生  放射科医生  B超医生  整形/美容  专科医生  综合门诊/全科医生  针灸/推拿  理疗师检验/化验  药剂师  其他（医生） 医院管理人员  护士长  护士  医助  导医  其他（医院管理/护理） 医疗器械研发  医疗器械临床实验  医疗设备生产/质量管理  医疗器械市场推广  医疗器械销售  医疗器械维修/售后  其他（医疗器械）"
        val dir_yiyao =
            "医药研发  生物工程/生物制药  化学分析员  药品临床实验  药品生产  药品质量管理 医药招商  药品市场推广  医药代表  医药销售经理/主管  药店店长  药店营业员  学术专员  其他（医药）"
        val dir_jiaoyu =
            "校长/园长  教务管理  教务人员  班主任/辅导员  中学教师  小学教师  幼教/早教 职业技术教师  家教  兼职教师  英语教师  音乐教师  美术教师  舞蹈教师  体育老师/教练  托管老师  钢琴老师  其他（教师）"
        val dir_peixun = "培训讲师  培训助理  学习管理师  培训经理/主管  培训督导  其他（培训）"
        val dir_zhuanyefuwu = "外语翻译  猎头/人才中介  律师/法务  咨询财务/会计  心理咨询师  企业管理咨询师"
        val dir_nengyuan = "电力/电气工程师  电气维修技术员  水利/水电工程师  制冷/暖通工程师 石油/天然气/煤炭技术人员  新能源工程师  其他（能源/电力/矿产）"
        val dir_huagong = "化工工程师  化工实验室研究员  涂料研发工程师  食品/饮料研发  安全工程师  日化产品研发  其他（化工）"
        val dir_fuzhuang = "服装设计师  工艺师  打样/制版  电脑放码员  质量管理  裁床  缝纫工  量体师  其他（服装/纺织）"
        val dir_huanbao = "环保工程师  污水处理工程师  环评工程师  其他（环保）"
        val dir_jinchukou = " 外贸经理/主管  外贸业务员  跟单员  单证员  报关/报检员  其他（进出口）"
        val dir_caigou = "采购经理/主管  采购员  买手  供应商开发  其他（采购）"
        val dir_wuliu = "物流经理/主管  物流专员  快递员  配货员/分拣员  供应链管理  调度员  货运代理业务  仓库主管/经理  仓库管理员  搬运工  船务/空运陆运操作  安检员 其他（物流/仓储）"
        val dir_siji = "商务司机  客运司机  货运司机  出租车司机  特种车司机  驾校教练/陪练代驾 网约车司机"
        val dir_nonglinmuyu = "场长  农艺师  饲养员  兽医  花艺师  园艺师  农业经理人  其他（农林牧渔）"
        val dir_qita = "科研人员  储备干部  兼职  实习生其他（其他职位）"

        initdirect(list, "销售", dir_xiaoshou)
        initdirect(list, "客服/售后", dir_kefu)
        initdirect(list, "市场/营销", dir_shichang)
        initdirect(list, "公关/媒介", dir_gongguan)
        initdirect(list, "人力资源", dir_renliziyuan)
        initdirect(list, "行政/后勤", dir_xingzheng)
        initdirect(list, "财务/审计/税务", dir_caiwu)
        initdirect(list, "律师/法务", dir_lvshi)
        initdirect(list, "高级管理", dir_gaojiguanli)
        initdirect(list, "生产/营运", dir_shengchan)
        initdirect(list, "工人（普工/技工）", dir_gongren)
        initdirect(list, "质控/安防", dir_zhikong)
        initdirect(list, "互联网、游戏、软件", dir_hulianwang)
        initdirect(list, "通信、硬件、电子电器", dir_tongxin)
        initdirect(list, "汽车、机械", dir_qiche)
        initdirect(list, "房地产、建筑、物业、装饰", dir_fangdichan)
        initdirect(list, "金融（金融/证券/投资）", dir_jinrong)
        initdirect(list, "银行", dir_yinhang)
        initdirect(list, "保险", dir_baoxian)
        initdirect(list, "广告", dir_guanggao)
        initdirect(list, "设计", dir_sheji)
        initdirect(list, "传媒（影视/媒体）", dir_chuanmei)
        initdirect(list, "写作/出版/印刷", dir_xiezuo)
        initdirect(list, "餐饮/娱乐", dir_canyin)
        initdirect(list, "酒店/旅游", dir_jiudian)
        initdirect(list, "美容/健身/运动", dir_meirong)
        initdirect(list, "保安/家政", dir_baoan)
        initdirect(list, "百货/零售", dir_baihuo)
        initdirect(list, "婚庆/活动", dir_hunqin)
        initdirect(list, "母婴/儿童", dir_muying)
        initdirect(list, "医疗", dir_yiliao)
        initdirect(list, "医药", dir_yiyao)
        initdirect(list, "教育（教师/教务）", dir_jiaoyu)
        initdirect(list, "培训", dir_peixun)
        initdirect(list, "专业服务", dir_zhuanyefuwu)
        initdirect(list, "能源/电力/矿产", dir_nengyuan)
        initdirect(list, "化工", dir_huagong)
        initdirect(list, "服装/纺织", dir_fuzhuang)
        initdirect(list, "环保", dir_huanbao)
        initdirect(list, "进出口", dir_jinchukou)
        initdirect(list, "采购", dir_caigou)
        initdirect(list, "物流/仓储", dir_wuliu)
        initdirect(list, "司机", dir_siji)
        initdirect(list, "农林牧渔", dir_nonglinmuyu)
        initdirect(list, "其他", dir_qita)

        for (key in list.keys) {

            if(key !in listKey)
            listKey.add(key)
//            Log.e("this","key = $key ,value = ${list.get(key)}")
        }

    }

    /**
     * 初始化hashmap的封装
     */
    private fun initdirect(list: HashMap<String, List<String>>, bigDirect: String, smallDirect: String) {
        val datas = initString(smallDirect)
        list.put(bigDirect, datas)

    }

    private fun initView() {
        binding.apply {
            drawerLayout = DirectDrawerlayout
            back = icDirectionBack
            lable = tvDirectionLabel
//            gridView = dvDirection
            drawer_content = drawerContent
            recycleView = rvDirection
        }

        /**
         * 返回
         */
        back.setOnClickListener {
            finish()
        }

        /**
         * 方向选择的点击
         */
        lable.setOnClickListener {
            drawerLayout.openDrawer(drawer_content)
        }
        /**
         * Drawerlayout的初始化
         */
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        /**
         * 初始化key的list
         */
//        val it = list.keys.iterator()
//        while (it.hasNext()) {
//            val key = it.next().toString()
//            listKey.add(key)
//        }

        /**
         * 初始化fragment
         */
        replaceFragment(DirectionFragment())

        /**
         * 初始化 recycleview,adapter,GridViewmanager
         */
        recycleView.layoutManager = manager
        mAdapter = GridViewAdapter(listKey, drawerLayout, this)
        recycleView.adapter = mAdapter


    }


    /**
     * 替换fragment
     */
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fl_direction, fragment)
        transaction.commit()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_direction
    }
}
