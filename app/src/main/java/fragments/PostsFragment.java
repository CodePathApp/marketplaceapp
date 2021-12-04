package fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marketplaceapp.Images;
import com.example.marketplaceapp.Post;
import com.example.marketplaceapp.PostsAdapter;
import com.example.marketplaceapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PostsFragment extends Fragment {

    public static final String TAG = "PostsFragment";
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    protected List<Images> allImages;
    protected HashMap<Post, List<Images>> allMap;
    //SwipeRefreshLayout swipeContainer;


    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG,"refreshing");
                queryPost();
            }
        });*/

        rvPosts = view.findViewById(R.id.rvPosts);

        allPosts = new ArrayList<>();
        allMap = new HashMap<Post, List<Images>>();
        adapter = new PostsAdapter(getContext(),allPosts,allMap);

        rvPosts.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(layoutManager);

        queryPost();


    }
    protected void queryPost() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.include(Post.KEY_OBJECT_ID);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts",e);
                    return;
                }
                HashMap<Post, List<Images>> map = new HashMap<>();
                for(Post post : posts ){
                    ParseQuery<Images> imageQuery = ParseQuery.getQuery(Images.class);
                    imageQuery.whereEqualTo("Post_id",post);
                    imageQuery.findInBackground(new FindCallback<Images>() {
                        @Override
                        public void done(List<Images> images, ParseException e) {
                            if(e != null){
                                Log.e(TAG, "Issue with getting posts",e);
                                return;
                            }
                            // Access the array of results here
                            //String firstItemId = itemList.get(0).getObjectId();
                            map.put(post,images);
                            adapter.clear();
                            adapter.addAll(posts,map);
                        }
                    });
                }
                //swipeContainer.setRefreshing(false);
                //allPosts.addAll(posts);
                //adapter.notifyDataSetChanged();
            }
        });
    }


}