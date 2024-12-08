// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 获取当前的用户 GET /api/users/currentUser */
export async function currentUser(options?: { [key: string]: any }) {
  return request<API.CurrentUser>('/api/users/currentUser', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 退出登录接口 POST /api/users/outLogin */
export async function outLogin(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/users/logout', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 登录接口 POST /api/users/login/ */
export async function login(body: API.LoginParams, options?: { [key: string]: any }) {
  return request<API.LoginResult>('/api/users/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  }).then(res => {
    const sessionID = res.sessionID;
    if(sessionID){
      localStorage.setItem('sessionID', sessionID)
    }
    return res;
  })
}

export async function register(body: API.RegisterParams){
  return request<API.RegisterResult>('/api/users/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
  }).then(res => {
    const sessionID = res.sessionID;
    if(sessionID){
      localStorage.setItem('sessionID', sessionID)
    }
    return res;
  })
}

/** 此处后端没有提供注释 GET /api/notices */
export async function getNotices(options?: { [key: string]: any }) {
  return request<API.NoticeIconList>('/api/notices', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取规则列表 GET /api/rule */
export async function rule(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.RuleList>('/api/rule', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 更新规则 PUT /api/rule */
export async function updateRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'POST',
    data:{
      method: 'update',
      ...(options || {}),
    }
  });
}

/** 新建规则 POST /api/rule */
export async function addRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'POST',
    data:{
      method: 'post',
      ...(options || {}),
    }
  });
}

/** 删除规则 DELETE /api/rule */
export async function removeRule(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/rule', {
    method: 'POST',
    data:{
      method: 'delete',
      ...(options || {}),
    }
  });
}

export const getProductList = (params: API.ProductPageParam) => {
  return request<API.ProductList>('/api/products/list', {
    method: 'POST',
    data:{
      ...params,
    }
  });
}

export const addProduct = (params: API.ProductListItem) => {
  return request<API.APIStatusObject>('/api/products/add', {
    method: 'POST',
    data:{
      ...params,
    }
  });
}

export const deleteProduct = (param: number) => {
  return request<API.APIStatusObject>(`/api/products/delete/${param}`, {
    method: 'DELETE',
  });
}

export const updateProduct = (params: API.ProductListItem) => {
  return request<API.APIStatusObject>('/api/products/update', {
    method: 'POST',
    data: {
      ...params
    }
  });
};

export const getProductLogList = (params: API.PageParams)=> {
  return request<API.ProductLogList>('/api/productLog/list', {
    method: 'POST',
    data: {
      ...params
    }
  });
}
