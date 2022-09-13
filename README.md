# ProfitableHerblore
Makes training herblore profitable.

Run this script on a members account with money and watch it burn through the green like Snoop Dogg. This script features:

-Grimy -> clean -> unf pot processing: Turns all invy/banked grimy/clean herbs into unf pots.

-Level checking: won't attempt to process or buy more of any herbs above your lvl (according to making unf potion requirement)

-Live price checking: buys/sells an item to know the very latest actively traded price, accounting for GE tax

-restocking: when all run out of herbs, sell all unf and price-check latest herb. If still profitable buy more, otherwise re-check prices of all herbs.  Also buys 13k vials of water if ran out

-Undercutting the market: Customize how much more to buy grimys and how much less to sell unf relative to live checked price (default 2gp)

-Profit margins: Specify how much each grimy -> unf potion should profit (default 50 gp per herb) before buying any more of them

-XP Mode or GP mode: Checks price for all available herbs that you can process into unf. If any herbs checked result in profit margin more than specified they are added to list of valid herbs to choose from. XP Mode sorts the valid herb list by highest XP per herb, GP mode sorts by highest profit margin (default GP mode)

-Max buy quantity: If your account ends up getting too rich but you don't want to buy infinite herbs when restocking, you can set maximum buy quantity per restock (default ~5000)

-Cuts the crap and goes to work: After putting up a buy offer, collects what goes through and processes it. This way the script can keep busy while waiting for buy offers to fulfill. However if a buy or sell offer takes too long it will get canceled and pricechecked again.

-Custom herb list: Select the herbs that are acceptable to you for the script to process

-Bot Mode: Decreases the mouse click interval to clean much faster (default OFF)  pending solution to actually do it

-Druidic Ritual completion: Buys required items then completes quest. Also buys some eyes of newt to boost lvl 3-22 herblore

-BONUS: This script was made for GP not XP. However if you buy eye of newt and guams in advance the script will make Attack Potion(3) and wont sell those.

-GUI! Just press start to go with defaults.

https://i.imgur.com/MFCEKuD.png

-Or launch via quickstart parameters. Available quickstart arguments:

maxbuy=<number>

profitmargin=<number>

undercutsell=<number>

undercutbuy=<number>

xpmode=true/false

botmode=true/false

herbs{herb1,herb2,herb3}  <---- if you specify this parameter, all other herbs will be disabled
