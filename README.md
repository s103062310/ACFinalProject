# ACFinalProject

## Notes
+ English~ Notes and Potential will have two language~XD
+ 中文~ Notes和Potential我都會寫兩個~ 請容忍我的破英文XD
+ Here is our real-time job assign, progress rate, and detailed spec.
+ 這裡是及時的工作分配&&進度還有詳細的spec
+ About the screenshot of player who is attacked, because we still don't known how to transmit images, we use message dialog first.
+ 關於傳送被炸者截圖，因為還不知道如何傳送圖片，就先都用文字對話框代替吧
+ If you think something interesting, you can add it in the Potential region.
+ 大家想到什麼有趣的都寫在Potential裡喔XD
+ If you have completed some functions, add an explanation under the file(will be submit as final readme) and delete old spec
+ 如果完成某些小功能就在各檔案的小標下面新增一條說明(當最後交出去的readme用)順便把舊的Spec刪掉

## Potential
+ tutorial of first play
+ 初次使用的遊戲教程
+ gift system(if you complete ?, you will get ?)
+ 階段禮物系統(完成多少圖片有什麼獎勵之類的)
+ multi language system
+ 多語言系統OvO~ ((Eva來個西班牙文XDD

## Work method -- important
+ In order to let everybody work at the same time, using the method below
+ 為了讓大家都可以隨心所欲地打扣，請用以下有點爛的方法~
+ if we modify the same file, git will show conflict error
+ 原因: git不讓大家改同一個檔案(會conflict)，但很有可能實作某項功能會改到
+ so, open another java project on your own computer(you will have two same project, one have git and another doesn't)
+ only modify the one doesn't have git
+ 在自己的電腦上另開一個project做修改
+ On FB, we will arrange who can push, if it is your turn, merge your code into the project which has git by yourself, then push
+ 在FB上先說好push的順序再push

## ScreenShot
![alt tag](/UserInterface.png)

## Server.java
+ deal with log in and create account(Eva)
	=> open a file to record all account data (including nickname password and game state)
	=> game state : Tera will define it~ just waite~ 
	=> the format of store file have to be designed
+ database of image name with its answer(Eva)
	=> also open a file to record all image name and its answer
	=> the format of store file have to be designed
+ implement answer check(Eva)
+ 與Client的溝通(果) => 主要設計整個protocal流程

## Client.java(果)
+ JFrame視窗
+ 與Server的溝通
+ 接收訊息後將工作分給不同區塊

## MainApplet.java
+ 1100*700
+ 組合所有子視窗

## Game.java(頻)
+ 800*450
+ 顯示圖片
+ 框圖片小工具，送出答案並接收結果
+ 倒數計時
+ 接收攻擊訊息並隨機產生色塊
+ 色塊隨時間退色

## Market.java(竹)
+ 800*200
+ 商品(圖片+名字+錢)
+ scrollbar
+ 按下圖片跳出攻擊視窗(把圖片做成類似按鈕效果(滑入變大什麼的))
+ 攻擊視窗: 顯示所有在線人(可攻擊列表) => 一樣是按鈕唷
+ 傳送攻擊(等protocal設計好使用)

## Scoreboard.java(雲)
+ 300*650
+ 標題 + 排行榜(個人圖像+暱稱+錢+(前3名有小圖案什麼的))
+ 及時更新(等protocal設計好使用)
+ 下面是個人資訊(可以多想一點:))
+ 依資料多寡決定是否出現scrollbar(5個人時可能就沒有，10個人會超出視窗就有)

## 給貝果的需求小整理
+ 需求: 收 => 送
+ 對答案: Client圖片名+答案 => 結果(成功/失敗)
+ 攻擊: 攻擊者+攻擊對象+顏色 => 攻擊訊息
+ 防禦: (若被攻擊者有防護罩) 防禦成功/攻擊成功 => 回報原攻擊者
+ 排行更新: 定時boardcast排行榜