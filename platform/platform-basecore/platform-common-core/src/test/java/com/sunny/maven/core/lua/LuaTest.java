package com.sunny.maven.core.lua;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * @author SUNNY
 * @description: lua调用测试
 * @create: 2022-12-03 15:58
 */
@Slf4j
public class LuaTest {

    @Test
    public void stringTest() {
        String luaStr = "print 'hello,world!'";
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.load(luaStr);
        chunk.call();

        String luaPath = "login.lua";
        globals.loadfile(luaPath).call();
        LuaValue func = globals.get(LuaValue.valueOf("hello"));
        func.call();
        LuaValue func1 = globals.get(LuaValue.valueOf("test"));
        String data = func1.call(LuaValue.valueOf("I'am from Java!")).toString();
        log.info("data return from lua is:{}", data);
    }
}
