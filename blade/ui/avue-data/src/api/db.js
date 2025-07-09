import {
  url
} from '@/config';
import crypto from '@/utils/crypto'
import request from '../axios';
const baseUrl = url + '/db'
export const getList = (params) => {
  return request({
    url: baseUrl + '/list',
    method: 'get',
    params: params
  })
}

export const getDetail = (id) => {
  return request({
    url: baseUrl + '/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: baseUrl + '/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: baseUrl + '/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: baseUrl + '/submit',
    method: 'post',
    data: row
  })
}
export const dynamicSql = (data) => {
  return request({
    url: baseUrl + '/dynamic-query',
    method: 'post',
    headers: {
      'data': data,
      'Content-Type': 'application/json'
    },
    data: data
  })
}
export const dbTest = (data) => {
  return request({
    url: baseUrl + '/db-test',
    method: 'post',
    headers: {
      'data': crypto.encrypt(JSON.stringify(data)),
      'Content-Type': 'application/json'
    },
    data: crypto.encrypt(JSON.stringify(data))
  })
}