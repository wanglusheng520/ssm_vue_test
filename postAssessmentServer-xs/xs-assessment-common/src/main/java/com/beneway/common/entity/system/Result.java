package com.beneway.common.entity.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Result {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private Object obj;
    private List<?> list;
    private String src;
    private Object data;
    private int count;


    public static Result success(){
        Result res = Result.builder()
                .code(0)
                .build();
        return res;
    }

    public static Result success(Object data){
        Result res = Result.builder()
                .code(0)
                .data(data)
                .build();
        return res;
    }

    public static Result fail(){
        Result res = Result.builder()
                .code(300)
                .build();
        return res;
    }

    public static Result fail(String msg){
        Result res = Result.builder()
                .code(300)
                .msg(msg)
                .build();
        return res;
    }

    public static Result check(Object data){
        if (null!=data){
            return success(data);
        }else{
            return fail();
        }
    }
    public static Result check(Integer data){
        if (data != 0){
            return success();
        }else{
            return fail();
        }
    }
}
