module(..., package.seeall)
-- 这里也可以require java提供的模块，本例没用到
function div(a, b)
    return math.floor(a / b)
end