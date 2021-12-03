package jp.ac.it_college.std.s20008.letquiz2

import retrofit2.Call
import retrofit2.http.GET

interface TopicRequest:Request {

    @GET("echo?user_content_key=shL_C0w5YPaGoVQ7kBlC5YtO9RKnll-ogW0raZnNA2Ptua53Ad5EysQw683ymLxL1w1BIo-qlS4saVGmWKqn4ar2eiYrWvlbm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnIfAdsIXIx8zs-a0ea6WJw4N8EpqvyQm-XbwVWSbjsDLbosb0_m1OWgo0_u-wpDcJsLhHbrSHo_mxFtqw32oGw6VNozfntwOPDGxFo93ZKFI&lib=M9Wjj4pVbriAqSweFVJ060LeUs6YUnKQP")
    fun getTopic(): Call<Topic>

}