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

## Collect image explanation
+ step1 download image and save it at src/resource/pic_ori
+ step2 open CreateImage.jave and run it(in tool package)
+ step3 check if there are strange images in src/resource/pic_rsc and delete original image in pic_ori
+ image's size must bigger than 800*450(better) or smaller(don't suggest)
+ it means width>800&&height>450 or width<800&&height<450

## 5/27 Integrate

### Server.java:
1. 結合帳戶系統
2. 設倒數計時器，定時更新排行榜
3. 從server傳該帳戶之Player給client (有1之後，現在的模式只是暫時)
4. 待Player定義完畢要檢查field是否都有set到
5. 攻擊完transmit screenshot

### Client.java:
1. 等有login系統後，server 創造一個player給client，每次登入都傳給client
2. Fail to establish I/O channel with server! => exception confirm
3. Correct => add money and display message(先用System.out)
4. Wrong => display message(先用System.out)
5. transmit screenshot after being attacked
6. try transmit list directly

### Timer.java: 
1. clock image!
2. if server need => develop other type timer

### Button.java:
1. image! (( 2 type

### Game.java:
1. answerCorrect()
2. answerWrong()
3. gameEnd()
4. start animate!

## 5/28 Integrate

### AttackWindow.java
1. 新增取消按鈕
2. 試做 scrollbar
3. 需要一點美工設計(?
4. 點選之後的後續連接(包含MainApplet裡)

### MainApplet.java
1. Attacked()
2. beAttacked()

### Scoreboard.java
1. 處理同分排名(是否給黃冠)
2. 調成喜歡的美工~

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