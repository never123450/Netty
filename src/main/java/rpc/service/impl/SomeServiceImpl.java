package rpc.service.impl;

import rpc.service.SomeService;

/**
 *
 * @description: 
 *
 * @author: xwy
 *
 * @create: 10:36 PM 2020/8/9
**/

public class SomeServiceImpl implements SomeService {
    @Override
    public String hello(String name) {
        return "hi," + name;
    }
}