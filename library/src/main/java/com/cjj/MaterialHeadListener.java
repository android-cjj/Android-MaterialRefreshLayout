package com.cjj;
/*
* Copyright (C) 2015 Pedro Paulo de Amorim
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
public interface MaterialHeadListener {
    void onComlete(MaterialRefreshLayout materialRefreshLayout);
    void onBegin(MaterialRefreshLayout materialRefreshLayout);
    void onPull(MaterialRefreshLayout materialRefreshLayout, float fraction);
    void onRelease(MaterialRefreshLayout materialRefreshLayout, float fraction);
    void onRefreshing(MaterialRefreshLayout materialRefreshLayout);
}
