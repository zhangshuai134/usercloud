--注意查找目录是从工程的根目录开始的
div  = require ("lua.div")
--引用java提供的方法，则直接写java的类名就好
jlib = require ("com.zs3.demo.lua.Math")
-- 模板方法
function genkey()
    tm = jlib.timestamp()
    a = div.div(tm, 1000)
    b = div.div(tm, 400)
    r = jlib.bitxor(a, b)
    return r
end